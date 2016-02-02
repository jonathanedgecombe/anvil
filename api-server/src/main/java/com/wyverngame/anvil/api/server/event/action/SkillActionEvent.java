package com.wyverngame.anvil.api.server.event.action;

import com.wurmonline.server.skills.Skill;
import com.wyverngame.anvil.api.server.action.ActionContext;

public final class SkillActionEvent extends ActionEvent<Void> {
	private final Skill skill;

	public SkillActionEvent(ActionContext ctx, Skill skill) {
		super(ctx);

		this.skill = skill;
	}

	public Skill getSkill() {
		return skill;
	}
}
