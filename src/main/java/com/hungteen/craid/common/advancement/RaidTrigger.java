package com.hungteen.craid.common.advancement;

import com.google.gson.JsonObject;
import com.hungteen.craid.CRaidUtil;
import net.minecraft.advancements.critereon.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

public class RaidTrigger extends SimpleCriterionTrigger<RaidTrigger.Instance> {

	private static final ResourceLocation ID = CRaidUtil.prefix("raid");
	public static final RaidTrigger INSTANCE = new RaidTrigger();

	public ResourceLocation getId() {
		return ID;
	}


	/**
	 * Deserialize a ICriterionInstance of this trigger from the data in the JSON.
	 */

	public void trigger(ServerPlayer player, String s) {
		this.trigger(player, (instance) -> {
			return instance.test(player, s);
		});
	}

	@Override
	protected Instance createInstance(JsonObject pJson, EntityPredicate.Composite pPlayer, DeserializationContext pContext) {
		StringPredicate amount = StringPredicate.deserialize(pJson.get("type"));
		return new Instance(pPlayer, amount);
	}

	public static class Instance extends AbstractCriterionTriggerInstance {
		private final StringPredicate type;

		public Instance(EntityPredicate.Composite player, StringPredicate res) {
			super(ID, player);
			this.type = res;
		}

		public boolean test(ServerPlayer player, String s) {
			return this.type.test(player, s);
		}


		@Override
		public JsonObject serializeToJson(SerializationContext pContext) {
			JsonObject jsonobject = new JsonObject();
			jsonobject.addProperty("type", this.type.serialize());
			return jsonobject;
		}
	}

}
