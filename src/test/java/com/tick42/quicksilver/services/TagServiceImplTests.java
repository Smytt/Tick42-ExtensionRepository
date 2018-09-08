package com.tick42.quicksilver.services;

import com.tick42.quicksilver.models.Tag;
import com.tick42.quicksilver.repositories.TagRepositoryImpl;
import com.tick42.quicksilver.repositories.base.TagRepository;
import com.tick42.quicksilver.security.JwtGenerator;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TagServiceImplTests {

    @Mock
    private TagRepositoryImpl tagRepository;
    @InjectMocks
    private TagServiceImpl tagService;


    @Test
    public void normalizeTags(){
        //Arrange
        String tag = "test tag normalize";
        String expectedTag = "test-tag-normalize";
        //Act
        tag = tagService.normalize(tag);

        //Assert
        Assert.assertEquals(tag,expectedTag);

    }

    @Test
    public void tags(){

        //Arrange
        Tag test = new Tag("test");
        Tag string = new Tag("string");
        Tag tag = new Tag("tags");
        String tagsString = "test,string,tags";

        when(tagRepository.findByName("test")).thenReturn(test);
        when(tagRepository.findByName("string")).thenReturn(string);
        when(tagRepository.findByName("tags")).thenReturn(tag);


        //Act
        List<Tag> tags = tagService.generateTags(tagsString);

        //Assert
        Assert.assertEquals(tags.size(), 3);
    }
}
