package com.decagonhq.clads.util

import com.decagonhq.clads.data.domain.ClientsListModel

object DummyDataUtil {
    fun populateClient(): ArrayList<ClientsListModel> {
        return arrayListOf(
            ClientsListModel("Ruth", "Unoka", "Lagos"),
            ClientsListModel(
                "Ezekiel",
                "Olufemi",
                "Benin"
            ),
            ClientsListModel(
                "Olufemi",
                "Ogundipe",
                "Abeokuta"
            ),
            ClientsListModel(
                "Adebayo",
                "Kings",
                "Lagos"
            ),
            ClientsListModel(
                "Abdul",
                "Salawu",
                "Benin"
            ),
            ClientsListModel(
                "Hope",
                "Omoruyi",
                "Abeokuta"
            )
        )
    }
}
