<?php

class ENTITY {

    const COMPANY = "company.company_id, company.phone, company.longitude, company.latitude, company.area_id, company.logo, company.image";
    const FIELD = "field.field_id, field.company_id, field.phone, field.hour_rate, field.open_time, field.close_time, field.area_x, field.area_y, field.max_capacity,field.auto_confirm";
    const AMENTITY = "amenity.amenity_id";
    const GAME_TYPE = "game_type.game_type_id, game_type.image, game_type.minimum_duration, game_type.increament_factor";
    const PLAYER = "player.player_id,player.name,player.phone,player.active,player.email,player.address,player.profile_picture,player.os";
}
