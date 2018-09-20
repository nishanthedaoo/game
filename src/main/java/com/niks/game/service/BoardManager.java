package com.niks.game.service;

import com.niks.game.Board;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Component
public class BoardManager {
    public ConcurrentMap<String,Board> boards= new ConcurrentHashMap<>();

}
