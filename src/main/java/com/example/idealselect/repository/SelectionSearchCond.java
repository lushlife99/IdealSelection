package com.example.idealselect.repository;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SelectionSearchCond {

    private String title;
    private String orderType;

    public SelectionSearchCond(String title, String orderType) {
        if(!title.trim().equals("")) {
            this.title = title;
        }

        if(!orderType.equals("POPULARITY") && !orderType.equals("LATEST")) {
            this.orderType = null;
        } else this.orderType = orderType;
        System.out.println("searchCond.title = " + this.title);
    }
}
