# Maqsam Task

This project is part of the initial assessment for the Junior Software Engineer position at Maqsam. The task involves writing a program that selects a desktop wallpaper based on the geographical location and time of the day.




## **Developer Notes**

I apologize if the project is large, but I tried to make it dynamic. I know I might get rejected because of its length, but I appreciate the attempt. Also, I saw that external libraries are restricted, so I had to build a library from scratch. Honestly, this is the first time a big company accepts my request and asks me for a task. I was a little nervous


To be honest, I didn’t fully understand the requirements, so I had to create a custom JSON library since external libraries are not allowed.

The JSON implementation is not complete and is still not stable, but it is stable for the necessary functionality required right now. I can improve and optimize it in the future.

Regarding the project, I tried to make it as flexible as possible.

**Currently, only the JSON object is supported. Unfortunately, I won't be able to complete the JSON array at this time due to time constraints**

## **Future Ideas:**
- Implement a feature to fetch data for a specified number of days, which will be configurable. This will enable automatic background changes through a queue scheduling task, making the process easier and simpler.
- Additionally, I might extend the project by adding an API that allows developers to add new features or even register custom functionalities, such as printing to stdout or changing the background or performing specific actions.
- I could also support it with an event bus, but I feel that it’s unnecessary because the project is simple. It serves as a task developer task for testing purposes, so we don’t want to overcomplicate it.

As for the JSON system, I could have made it better, but my schedule is very tight, and I wanted to complete the task as quickly as possible while delivering the best possible result. Hopefully, I will get accepted.

Regarding the asset manager, it’s not the best, but I tried to implement it with the "Keep It Simple, Stupid" (KISS) principle.