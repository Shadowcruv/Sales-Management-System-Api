package com.example.emailVerificationPractice.Entity.Helper;

import com.example.emailVerificationPractice.Entity.Item;
import lombok.Getter;
import lombok.Setter;

import java.util.Collection;

@Getter
@Setter
public class SaleItemProjection {

    private final long client_id;
    private final Collection<Item> itemList;


    public SaleItemProjection(long clientId, Collection<Item> itemList) {
        client_id = clientId;
        this.itemList = itemList;
    }
}
