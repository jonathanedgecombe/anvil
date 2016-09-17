package com.wyverngame.anvil.api.server.event;

import com.wurmonline.server.questions.KarmaQuestion;
import com.wyverngame.anvil.api.event.Event;

import java.util.Properties;

public class KarmaQuestionAnswerEvent extends Event<Void> {
	private final KarmaQuestion question;
	private final Properties answer;

	public KarmaQuestionAnswerEvent(KarmaQuestion question, Properties answer) {
		this.question = question;
		this.answer = answer;
	}

	public KarmaQuestion getQuestion() {
		return question;
	}

	public Properties getAnswer() {
		return answer;
	}
}
