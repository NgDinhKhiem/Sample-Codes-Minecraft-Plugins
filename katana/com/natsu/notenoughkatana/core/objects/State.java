package com.natsu.notenoughkatana.core.objects;

public enum State {
    WITHERED,
    SHARP,
    PRISTINE,
    SHINY,
    GOOD,
    MAIMED,
    SHATTERED,
    DESTROYED,
    BROKEN;
    public State next() {
        switch (this) {
            case PRISTINE:
            case WITHERED:
                return SHINY;
            case SHINY:
            case SHARP:
                return GOOD;
            case GOOD:
                return MAIMED;
            case MAIMED:
                return SHATTERED;
            case SHATTERED:
                return DESTROYED;
            case DESTROYED:
            case BROKEN:
                return BROKEN;
        }
        return BROKEN;
    }

    public State repair() {
        switch (this){
            case PRISTINE:
            case WITHERED:
                return PRISTINE;
            case SHINY:
            case SHARP:
                return PRISTINE;
            case GOOD:
                return SHINY;
            case BROKEN:
                return DESTROYED;
            case DESTROYED:
                return SHATTERED;
            case SHATTERED:
                return MAIMED;
            case MAIMED:
                return GOOD;
        }
        return GOOD;
    }
}
