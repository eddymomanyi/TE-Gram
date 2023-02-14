package com.techelevator.controller;

import com.techelevator.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
public class SearchController {

    @Autowired
    private UserDao dao;

    @RequestMapping(path="/search/users", method = RequestMethod.GET)
    public List<String> getAllUsernames() {
        return dao.getAllUsernames();
    }
}
