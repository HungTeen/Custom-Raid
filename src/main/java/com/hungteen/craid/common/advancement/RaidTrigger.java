package com.hungteen.craid.common.advancement;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.hungteen.craid.CRaidUtil;

import net.minecraft.advancements.criterion.AbstractCriterionTrigger;
import net.minecraft.advancements.criterion.CriterionInstance;
import net.minecraft.advancements.criterion.EntityPredicate;
import net.minecraft.advancements.criterion.EntityPredicate.AndPredicate;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.loot.ConditionArrayParser;
import net.minecraft.util.ResourceLocation;

public class RaidTrigger extends AbstractCriterionTrigger<RaidTrigger.Instance> {

	private static final ResourceLocation ID = CRaidUtil.prefix("raid");
	public static final RaidTrigger INSTANCE = new RaidTrigger();
	
	public ResourceLocation getId() {
		return ID;
	}

	/**
	 * Deserialize a ICriterionInstance of this trigger from the data in the JSON.
	 */
	@Override
	protected Instance createInstance(JsonObject json, AndPredicate player,
			ConditionArrayParser p_230241_3_) {
		StringPredicate amount = StringPredicate.deserialize(json.get("type"));
		return new Instance(player, amount);
	}

	public void trigger(ServerPlayerEntity player, String s) {
		this.trigger(player, (instance) -> {
			return instance.test(player, s);
		});
	}

	public static class Instance extends CriterionInstance {
		private final StringPredicate type;

		public Instance(EntityPredicate.AndPredicate player, StringPredicate res) {
			super(ID, player);
			this.type = res;
		}

		public boolean test(ServerPlayerEntity player, String s) {
			return this.type.test(player, s);
		}

		public JsonElement func_200288_b() {
			JsonObject jsonobject = new JsonObject();
			jsonobject.addProperty("type", this.type.serialize());
			return jsonobject;
		}
	}

}