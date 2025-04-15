package com.example.parchis;

public enum GameState {
    JOINING, // Players are joining the game
    INITIALIZING, // Game is initializing
    IN_PROGRESS, // Game is in progress
    FINISHED, // Game is finished
    ERROR, // An error occurred
    PAUSED, // Game is paused
    CANCELED, // Game is canceled
}
