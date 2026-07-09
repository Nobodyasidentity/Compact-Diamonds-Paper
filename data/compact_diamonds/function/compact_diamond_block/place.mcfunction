setblock ~ ~ ~ minecraft:ancient_debris replace
summon minecraft:item_display ~0.5 ~0.5 ~0.5 {brightness:{sky:15,block:15},Tags:["compact_diamond_block"],item:{components:{"minecraft:enchantment_glint_override":1b,"minecraft:custom_model_data":{strings:["compact_diamond_block"]}},count:1,id:"minecraft:diamond_block"},transformation:{left_rotation:[0.0f,0.0f,0.0f,1.0f],right_rotation:[0.0f,0.0f,0.0f,1.0f],scale:[1.01f,1.01f,1.01f],translation:[0.0f,0.0f,0.0f]}}
tp @s 0 -9999 0
kill @s
