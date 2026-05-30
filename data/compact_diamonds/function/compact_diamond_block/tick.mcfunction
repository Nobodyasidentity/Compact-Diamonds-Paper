# Placing
execute as @e[tag=compact_diamond_block,type=minecraft:marker] at @s align xyz run function compact_diamonds:compact_diamond_block/place
# Breaking
execute at @e[tag=compact_diamond_block,type=item_display] unless block ~ ~ ~ minecraft:ancient_debris run kill @e[type=item,nbt={Item:{"id":"minecraft:ancient_debris"}},distance=..0.7]
execute as @e[tag=compact_diamond_block,type=item_display] at @s align xyz unless block ~ ~ ~ minecraft:ancient_debris run function compact_diamonds:compact_diamond_block/break
