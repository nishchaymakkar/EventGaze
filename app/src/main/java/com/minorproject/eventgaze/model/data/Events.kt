package com.minorproject.eventgaze.model.data

import androidx.annotation.DrawableRes
import com.minorproject.eventgaze.R

data class Event(
   @DrawableRes val image: Int,
   @DrawableRes val profileimg: Int,
    val id: Int,
    val categoryid: Int,
    val title: String,
    val des: String,
    val publishername: String
)
val events = listOf(
    Event(R.drawable.img_1,R.drawable.img,1, 2, "Soccer Tournament","\"There is no one who loves pain itself, who seeks after it and wants to have it, simply because it is pain...\"\n" +
            "What is Lorem Ipsum?\n" +
            "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.\n" +
            "\n", "IIT Selampur"),
    Event(R.drawable.img_2,R.drawable.img,2, 3, "Music Concert","\"There is no one who loves pain itself, who seeks after it and wants to have it, simply because it is pain...\"\n" +
            "What is Lorem Ipsum?\n" +
            "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.\n" +
            "\n","Music Society"),
    Event(R.drawable.img_3,R.drawable.img,3, 4, "Debate Championship","\"There is no one who loves pain itself, who seeks after it and wants to have it, simply because it is pain...\"\n" +
            "What is Lorem Ipsum?\n" +
            "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.\n" +
            "\n","Debate Society"),
    Event(R.drawable.img_4,R.drawable.img,4, 5, "Tech Expo","\"There is no one who loves pain itself, who seeks after it and wants to have it, simply because it is pain...\"\n" +
            "What is Lorem Ipsum?\n" +
            "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.\n" +
            "\n","Technical Society"),
    Event(R.drawable.img_1,R.drawable.img,5, 2, "Basketball Tournament","\"There is no one who loves pain itself, who seeks after it and wants to have it, simply because it is pain...\"\n" +
            "What is Lorem Ipsum?\n" +
            "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.\n" +
            "\n","Sports Society")
)