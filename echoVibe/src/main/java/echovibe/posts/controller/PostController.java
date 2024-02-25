package echovibe.posts.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import echovibe.Config.FileUtility;
import echovibe.Config.GenericDataDto;
import echovibe.Config.MessageContants;
import echovibe.Config.UrlConstants;
import echovibe.posts.domain.Post;
import echovibe.posts.model.PostData;
import echovibe.posts.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Arrays;

@RestController
@RequestMapping(UrlConstants.BASE_URL + UrlConstants.POST)
public class PostController {
    @Autowired
    private PostService service;
    @Autowired
    private FileUtility fileUtility;

    String path = "D:\\Raapyd\\Java\\";

    @PostMapping("/save")
    public GenericDataDto save(@RequestParam String post, @RequestParam(value = "file", required = true) MultipartFile file, HttpServletRequest req) throws IOException {
        GenericDataDto genericDataDto = new GenericDataDto();
        genericDataDto.setResponseCode(HttpStatus.OK.value());
        genericDataDto.setResponseMessage(MessageContants.SUCCESS);
        Post entityDto = new ObjectMapper().registerModule(new JavaTimeModule()).readValue(post, Post.class);
        entityDto.setCreateDate(LocalDate.now());
        if(null!=file){
            entityDto.setPostImage(fileUtility.saveFileToServer(file,path));
        }
        genericDataDto.setData(service.saveEntity(entityDto));
        return genericDataDto;
    }

    @PutMapping("/update")
    public GenericDataDto update(@RequestBody String post, @RequestParam(value = "file", required = true) MultipartFile file, HttpServletRequest req) throws IOException {
        GenericDataDto genericDataDto = new GenericDataDto();
        genericDataDto.setResponseCode(HttpStatus.OK.value());
        genericDataDto.setResponseMessage(MessageContants.SUCCESS);
        Post entityDto = new ObjectMapper().registerModule(new JavaTimeModule()).readValue(post, Post.class);
        Post post1 = service.getById(entityDto.getId());
        if(null!=file){
            fileUtility.removeFileAtServer(post1.getPostImage(),path);
            entityDto.setPostImage(fileUtility.saveFileToServer(file,path));
        }
        entityDto = service.updateEntity(entityDto);
        if (null != entityDto) {
            genericDataDto.setData(entityDto);
        } else {
            genericDataDto.setResponseMessage(MessageContants.NOT_FOUND);
            genericDataDto.setResponseCode(HttpStatus.NOT_FOUND.value());
        }
        return genericDataDto;
    }

    @GetMapping("/")
    public GenericDataDto getAll() {
        GenericDataDto genericDataDto = new GenericDataDto();
        genericDataDto.setResponseCode(HttpStatus.OK.value());
        genericDataDto.setResponseMessage(MessageContants.SUCCESS);
        genericDataDto.setDatalist(Arrays.asList(service.getUsers().toArray()));
        return genericDataDto;
    }

    @GetMapping("/{id}")
    public GenericDataDto getById(@PathVariable String id) {
        GenericDataDto genericDataDto = new GenericDataDto();
        genericDataDto.setResponseCode(HttpStatus.OK.value());
        genericDataDto.setResponseMessage(MessageContants.SUCCESS);
        Post domain = service.getById(id);
        if (null != domain) {
            genericDataDto.setData(domain);
        } else {
            genericDataDto.setResponseCode(HttpStatus.NOT_FOUND.value());
            genericDataDto.setResponseMessage(MessageContants.NOT_FOUND);
        }
        return genericDataDto;
    }

    @DeleteMapping("/{id}")
    public GenericDataDto delete(@PathVariable String id) {
        GenericDataDto genericDataDto = new GenericDataDto();
        genericDataDto.setResponseMessage(MessageContants.SUCCESS);
        genericDataDto.setResponseCode(HttpStatus.OK.value());
        Post domain = service.deleteEntity(id);
        if (null == domain) {
            genericDataDto.setResponseMessage(MessageContants.NOT_FOUND);
            genericDataDto.setResponseCode(HttpStatus.NOT_FOUND.value());
        }
        return genericDataDto;
    }


    @PostMapping("/addComment")
    public GenericDataDto addComment(@RequestBody PostData entityDto) {
        entityDto.setCreateDate(LocalDate.now());
        GenericDataDto genericDataDto = new GenericDataDto();
        genericDataDto.setResponseCode(HttpStatus.OK.value());
        genericDataDto.setResponseMessage(MessageContants.SUCCESS);
        Post domain = service.addComment(entityDto);
        if (null == domain) {
            genericDataDto.setResponseMessage(MessageContants.NOT_FOUND);
            genericDataDto.setResponseCode(HttpStatus.NOT_FOUND.value());
            return genericDataDto;
        }
        genericDataDto.setData(domain);
        return genericDataDto;
    }

    @PostMapping("/addLike")
    public GenericDataDto addLike(@RequestBody PostData entityDto) {
        entityDto.setCreateDate(LocalDate.now());
        GenericDataDto genericDataDto = new GenericDataDto();
        genericDataDto.setResponseCode(HttpStatus.OK.value());
        genericDataDto.setResponseMessage(MessageContants.SUCCESS);
        Post domain = service.addLike(entityDto);
        if (null == domain) {
            genericDataDto.setResponseMessage(MessageContants.NOT_FOUND);
            genericDataDto.setResponseCode(HttpStatus.NOT_FOUND.value());
            return genericDataDto;
        }
        genericDataDto.setData(domain);
        return genericDataDto;
    }

    @GetMapping("/files/{filename}")
    public ResponseEntity<Resource> getFile(@PathVariable String filename) {
        try {
            // Resolve file path
            Path filePath = Paths.get(path).resolve(filename).normalize();
            Resource resource = new UrlResource(filePath.toUri());

            // Check if the file exists
            if (resource.exists() || resource.isReadable()) {
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                        .body(resource);
            } else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "File not found: " + filename);
            }
        } catch (MalformedURLException ex) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Could not read file: " + filename, ex);
        }
    }
}
