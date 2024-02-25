package echovibe.members.controller;

import echovibe.Config.GenericDataDto;
import echovibe.Config.MessageContants;
import echovibe.Config.UrlConstants;
import echovibe.members.domain.Members;
import echovibe.members.service.MembersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Arrays;

@RestController
@RequestMapping(UrlConstants.BASE_URL + UrlConstants.MEMBER)
public class MembersController {
    @Autowired
    private MembersService service;

    @PostMapping("/save")
    public GenericDataDto save(@RequestBody Members entityDto) {
        entityDto.setCreateDate(LocalDate.now());
        GenericDataDto genericDataDto = new GenericDataDto();
        genericDataDto.setResponseCode(HttpStatus.OK.value());
        genericDataDto.setResponseMessage(MessageContants.SUCCESS);
        genericDataDto.setData(service.saveEntity(entityDto));
        return genericDataDto;
    }

    @PutMapping("/update")
    public GenericDataDto update(@RequestBody Members entityDto) {
        GenericDataDto genericDataDto = new GenericDataDto();
        genericDataDto.setResponseCode(HttpStatus.OK.value());
        genericDataDto.setResponseMessage(MessageContants.SUCCESS);
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
        Members itemCategories = service.getById(id);
        if (null != itemCategories) {
            genericDataDto.setData(itemCategories);
        } else {
            genericDataDto.setResponseCode(HttpStatus.NOT_FOUND.value());
            genericDataDto.setResponseMessage(MessageContants.NOT_FOUND);
        }
        return genericDataDto;
    }

    @DeleteMapping("/{id}")
    public GenericDataDto delete(@PathVariable String id) {
        GenericDataDto genericDataDto = new GenericDataDto();
        Members domain = service.deleteEntity(id);
        if (null != domain) {
            genericDataDto.setResponseMessage(MessageContants.SUCCESS);
            genericDataDto.setResponseCode(HttpStatus.OK.value());
        } else {
            genericDataDto.setResponseMessage(MessageContants.NOT_FOUND);
            genericDataDto.setResponseCode(HttpStatus.NOT_FOUND.value());
        }
        return genericDataDto;
    }

}
