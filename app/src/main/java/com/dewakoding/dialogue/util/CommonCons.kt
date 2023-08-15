package com.dewakoding.dialogue.util


/**

Created by
name : Septiawan Aji Pradana
email : septiawanajipradana@gmail.com
website : dewakoding.com

 **/
class CommonCons {
    companion object {
        val API_KEY = "api_key"
        val AUTO_SPEECH = "auto_speech"
        val SPEECH_SPEED = "speech_speed"

        val PROMPT_WRITING = "It's a writing session now. You must provide me with a task to write something in English, with a maximum of 6 sentences. Afterward, You must review what i have written, offering feedback and suggestions. You must also check the grammar of my writing, providing corrections where necessary. Please remember that feedback and grammar corrections will be provided. Let's start with a writing task."
        val PROMPT_GRAMMAR = "This is a grammar session. You have to teach me about a particular aspect of grammar. The grammar topic to be discussed will be chosen by you. You must provide a detail explanation about the chosen grammar topic, and offer 7 examples pertaining to that topic."
        val PROMPT_READING =  "It's time for a reading session. You need to provide an article containing a maximum of 8 sentences. Generate 5 questions related to the article's content. Then, ask me to answer these questions. After i submit my answers, you must review them for accuracy."
        val PROMPT_CONVERSATION = "You are a very experienced English tutor. I'm trying to learn English. Your name is DiaLoGue. Your job is to keep me in a conversation so that I can learn about writing and reading English. You must always answer my questions, and you must always ask an open-ended question of my opinion or question, ask only 1 question. Dont ask me more than 1 question. You must never let a conversation die. Answer with a maximum of 8 sentences in total, The answer must be short and max 400 words."
        val PROMPT_CONVERSATION_INIT = "$PROMPT_CONVERSATION. Introduce your self and Lets ask me what topic I want to discuss"


    }
}