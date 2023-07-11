package io.github.landuo.cq.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author accidia
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum CommandEnum {
    /**
     * 
     */
    HELP("help", "查看帮助, 所有命令以 . 或者 。开头, 命令可带参数时用空格区分"),
    SCHEDULES_V2("图2", "查询splatoon2的地图时间表, 后接时间的小时可查询该时间的对战. \n如： 图2 18, 查询18:00-20:00的安排"),
    RANDOM_WEAPON_V2("随机武器2", "查询splatoon2的随机武器"),
    WEAPON_CATE_V2("武器分类2", "不加参数时查询所有splatoon2武器分类;\n 可查询某类武器的所有武器,例如: 武器分类2 水枪"),
    WEAPON_V2("武器2", "查询指定武器, 例如: 武器2 红管"), SALMON_V2("工2", "查询splatoon2的打工时刻表"),
    BRANDS_V2("品牌2", "查看splatoon2的所有装备品牌"), KEYWORD("监控", "咕咕监控关键字"),
    SCHEDULES_V3("图3", "查询splatoon3地图时间表, 后接时间的小时可查询该时间的对战. \n如： 图3 18, 查询18:00-20:00的安排"),
    RANDOM_WEAPON_V3("随机武器3", "查询splatoon3的随机武器"),
    WEAPON_CATE_V3("武器分类3", "不加参数时查询所有武器splatoon3分类;\n 可查询某类武器的所有武器,例如: 武器分类3 水枪"),
    WEAPON_V3("武器3", "查询splatoon3指定武器, 例如: 武器3 红管"), SALMON_V3("工3", "查询splatoon3的打工时刻表"),
    BRANDS_V3("品牌3", "查看splatoon3的所有装备品牌"), LETOU("乐透", "随机生成大乐透投注，后加数字时可批量生成投注"), L4D2_EXEC("players", "发送指令到L4D2服务器");

    private String name;
    private String desc;
}
