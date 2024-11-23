package com.example.title.models;

public class StatisticTitleModel {
    private StatisiticModel statisticDTO;
    private TitleModel titleDTO;

    public StatisticTitleModel(StatisiticModel statisticDTO, TitleModel titleDTO) {
        this.statisticDTO = statisticDTO;
        this.titleDTO = titleDTO;
    }

    public StatisiticModel getStatisticDTO() {
        return statisticDTO;
    }

    public void setStatisticDTO(StatisiticModel statisticDTO) {
        this.statisticDTO = statisticDTO;
    }

    public TitleModel getTitleDTO() {
        return titleDTO;
    }

    public void setTitleDTO(TitleModel titleDTO) {
        this.titleDTO = titleDTO;
    }
}
