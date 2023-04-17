package com.example.api_example.data

import com.example.api_example.R
import com.example.api_example.ui.Clothes
import java.util.*

object dataManger {
    val summerList1 = mutableListOf(
        Clothes(R.drawable.shirt1),
        Clothes(R.drawable.shirt2),
        Clothes(R.drawable.shirt3),
        Clothes(R.drawable.short2),
        Clothes(R.drawable.short1),
        Clothes(R.drawable.short3),
        Clothes(R.drawable.sneaker1),
        Clothes(R.drawable.sneaker2),
        Clothes(R.drawable.sneaker3),
    )

    val summerList2 = mutableListOf(
        Clothes(R.drawable.shirt3),
        Clothes(R.drawable.shirt2),
        Clothes(R.drawable.shirt1),
        Clothes(R.drawable.short1),
        Clothes(R.drawable.short1),
        Clothes(R.drawable.short3),
        Clothes(R.drawable.sneaker3),
        Clothes(R.drawable.sneaker2),
        Clothes(R.drawable.sneaker1)
    )

    val winterList1 = mutableListOf(
        Clothes(R.drawable.shirt32),
        Clothes(R.drawable.shirt33),
        Clothes(R.drawable.shite31),
        Clothes(R.drawable.pants1),
        Clothes(R.drawable.pants2),
        Clothes(R.drawable.pants3),
        Clothes(R.drawable.sneaker6),
        Clothes(R.drawable.shoes5),
        Clothes(R.drawable.sneaker3),
    )

    val winterList2 = mutableListOf(
        Clothes(R.drawable.shirt33),
        Clothes(R.drawable.shirt32),
        Clothes(R.drawable.shite31),
        Clothes(R.drawable.pants2),
        Clothes(R.drawable.pants1),
        Clothes(R.drawable.pants3),
        Clothes(R.drawable.sneaker3),
        Clothes(R.drawable.sneaker2),
        Clothes(R.drawable.sneaker6),
    )
    var summerClothes = mutableListOf(summerList1, summerList2)
    var winterClothes = mutableListOf(winterList1, winterList2)
    val random = Random()
    val randomIndex = random.nextInt(summerClothes.size)
    var randomListSummer = summerClothes[randomIndex]
    var randomListWinter = winterClothes[randomIndex]
}