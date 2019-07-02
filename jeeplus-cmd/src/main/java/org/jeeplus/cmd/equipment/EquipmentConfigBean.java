package org.jeeplus.cmd.equipment;
/**
 * 设备配置Bean
 * @author Jin
 *
 */
public class EquipmentConfigBean {

	/** 设备名称 */
	private String companyName;
	/** 识别距离 */
	private int identifyDistance = 1;
	/** 识别分数 */
	private int identifyScores = 80;
	/** 识别记录时间窗 */
	private int saveIdentifyTime = 0;
	/** 语音播报模式 */
	private int ttsModType = 100;
	/** 语音播报模式自定义内容 */
	private String ttsModContent="欢迎{name}";
	/** 串口输出模式 */
	private int comModType = 100;
	/** 串口输出模式自定义内容 */
	private String comModContent = "hello";
	/** 屏幕显示模式 */
	private int displayModType = 100;
	/** 屏幕显示模式自定义内容 */
	private String displayModContent = "{name}欢迎你";
	/** 标语（大屏展示） */
	private String slogan;
	/** 公司简介 */
	private String intro;
	/** 陌生人判定 */
	private int recStrangerTimesThreshold=3;
	/** 陌生人开关（是否进行陌生人识别）1:不识别（陌生人不会记录结果） 2识别 */
	private int recStrangerType = 2;
	/** 陌生人语音播报模式 */
	private int ttsModStrangerType = 100;
	/** 陌生人语音播报模式自定义内容 */
	private String ttsModStrangerContent="陌生人啊你好";
	/** 多个人脸检测设置 */
	private int multiplayerDetection = 1;
	/** Uface-M72XX设备默认输出韦根26信号，可修改参数wg传入值输出韦根34信号 */
	private String wg = "#WG{idcardNum}#";
	/** 识别等级 */
	private int recRank = 2;
	/** 继电器控制开门到关门的时间间隔，单位ms */
	private int delayTimeForCloseDoor = 500;
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public int getIdentifyDistance() {
		return identifyDistance;
	}
	public void setIdentifyDistance(int identifyDistance) {
		this.identifyDistance = identifyDistance;
	}
	public int getIdentifyScores() {
		return identifyScores;
	}
	public void setIdentifyScores(int identifyScores) {
		this.identifyScores = identifyScores;
	}
	public int getSaveIdentifyTime() {
		return saveIdentifyTime;
	}
	public void setSaveIdentifyTime(int saveIdentifyTime) {
		this.saveIdentifyTime = saveIdentifyTime;
	}
	public int getTtsModType() {
		return ttsModType;
	}
	public void setTtsModType(int ttsModType) {
		this.ttsModType = ttsModType;
	}
	public String getTtsModContent() {
		return ttsModContent;
	}
	public void setTtsModContent(String ttsModContent) {
		this.ttsModContent = ttsModContent;
	}
	public int getComModType() {
		return comModType;
	}
	public void setComModType(int comModType) {
		this.comModType = comModType;
	}
	public String getComModContent() {
		return comModContent;
	}
	public void setComModContent(String comModContent) {
		this.comModContent = comModContent;
	}
	public int getDisplayModType() {
		return displayModType;
	}
	public void setDisplayModType(int displayModType) {
		this.displayModType = displayModType;
	}
	public String getDisplayModContent() {
		return displayModContent;
	}
	public void setDisplayModContent(String displayModContent) {
		this.displayModContent = displayModContent;
	}
	public String getSlogan() {
		return slogan;
	}
	public void setSlogan(String slogan) {
		this.slogan = slogan;
	}
	public String getIntro() {
		return intro;
	}
	public void setIntro(String intro) {
		this.intro = intro;
	}
	public int getRecStrangerTimesThreshold() {
		return recStrangerTimesThreshold;
	}
	public void setRecStrangerTimesThreshold(int recStrangerTimesThreshold) {
		this.recStrangerTimesThreshold = recStrangerTimesThreshold;
	}
	public int getRecStrangerType() {
		return recStrangerType;
	}
	public void setRecStrangerType(int recStrangerType) {
		this.recStrangerType = recStrangerType;
	}
	public int getTtsModStrangerType() {
		return ttsModStrangerType;
	}
	public void setTtsModStrangerType(int ttsModStrangerType) {
		this.ttsModStrangerType = ttsModStrangerType;
	}
	public String getTtsModStrangerContent() {
		return ttsModStrangerContent;
	}
	public void setTtsModStrangerContent(String ttsModStrangerContent) {
		this.ttsModStrangerContent = ttsModStrangerContent;
	}
	public int getMultiplayerDetection() {
		return multiplayerDetection;
	}
	public void setMultiplayerDetection(int multiplayerDetection) {
		this.multiplayerDetection = multiplayerDetection;
	}
	public String getWg() {
		return wg;
	}
	public void setWg(String wg) {
		this.wg = wg;
	}
	public int getRecRank() {
		return recRank;
	}
	public void setRecRank(int recRank) {
		this.recRank = recRank;
	}
	public int getDelayTimeForCloseDoor() {
		return delayTimeForCloseDoor;
	}
	public void setDelayTimeForCloseDoor(int delayTimeForCloseDoor) {
		this.delayTimeForCloseDoor = delayTimeForCloseDoor;
	}
	
	
}
