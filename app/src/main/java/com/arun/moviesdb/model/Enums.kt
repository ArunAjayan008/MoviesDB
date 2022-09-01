package com.arun.moviesdb.model

class Enums {

    enum class PageState {
        LOADING, FAILED, SUCCESS, NONE, ERROR, EMPTY, DEFAULT, OVERLAY
    }

    enum class ErrorType {
        SERVER_ERROR, NO_INTERNET, SERVER_TIMEOUT,NONE
    }
}