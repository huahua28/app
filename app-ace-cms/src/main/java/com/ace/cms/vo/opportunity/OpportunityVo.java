package com.ace.cms.vo.opportunity;

import java.util.Date;

import com.ace.cms.vo.user.Author;
import com.ace.cms.vo.user.UserVo;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OpportunityVo {
	public interface Summary{}
	public interface Detail extends Summary{}
	
	 	private Long opportunityId;
	    private Author user;
	    private String createTime;
	    private String logo;
	    private String title;
	    private int locale;
	    private int direction;
	    private int stage;
	    private String highlight;
	    private String description;
	    private int member;
	    private int finance;
	    private float money;
	    private String recruitment;
	    private String pic1;
	    private String pic2;
	    private String pic3;
	    private String url1;
	    private String url2;
	    private String url3;
	    private String finishTime;
	    private float price;
	    
	    
	    @JsonView(OpportunityVo.Summary.class)
		public Long getOpportunityId() {
			return opportunityId;
		}
		public void setOpportunityId(Long opportunityId) {
			this.opportunityId = opportunityId;
		}
		@JsonView(OpportunityVo.Summary.class)
		public Author getUser() {
			return user;
		}
		public void setUser(Author user) {
			this.user = user;
		}
		@JsonView(OpportunityVo.Summary.class)
		public String getCreateTime() {
			return createTime;
		}
		public void setCreateTime(String createTime) {
			this.createTime = createTime;
		}
		@JsonView(OpportunityVo.Summary.class)
		public String getLogo() {
			return logo;
		}
		public void setLogo(String logo) {
			this.logo = logo;
		}
		@JsonView(OpportunityVo.Summary.class)
		public String getTitle() {
			return title;
		}
		public void setTitle(String title) {
			this.title = title;
		}
		@JsonView(OpportunityVo.Summary.class)
		public int getLocale() {
			return locale;
		}
		public void setLocale(int locale) {
			this.locale = locale;
		}
		@JsonView(OpportunityVo.Summary.class)
		public int getDirection() {
			return direction;
		}
		public void setDirection(int direction) {
			this.direction = direction;
		}
		@JsonView(OpportunityVo.Summary.class)
		public int getStage() {
			return stage;
		}
		public void setStage(int stage) {
			this.stage = stage;
		}
		@JsonView(OpportunityVo.Detail.class)
		public String getHighlight() {
			return highlight;
		}
		public void setHighlight(String highlight) {
			this.highlight = highlight;
		}
		@JsonView(OpportunityVo.Detail.class)
		public String getDescription() {
			return description;
		}
		public void setDescription(String description) {
			this.description = description;
		}
		@JsonView(OpportunityVo.Detail.class)
		public int getMember() {
			return member;
		}
		public void setMember(int member) {
			this.member = member;
		}
		@JsonView(OpportunityVo.Detail.class)
		public int getFinance() {
			return finance;
		}
		public void setFinance(int finance) {
			this.finance = finance;
		}
		@JsonView(OpportunityVo.Detail.class)
		public float getMoney() {
			return money;
		}
		public void setMoney(float money) {
			this.money = money;
		}
		@JsonView(OpportunityVo.Detail.class)
		public String getRecruitment() {
			return recruitment;
		}
		public void setRecruitment(String recruitment) {
			this.recruitment = recruitment;
		}
		@JsonView(OpportunityVo.Detail.class)
		public String getPic1() {
			return pic1;
		}
		public void setPic1(String pic1) {
			this.pic1 = pic1;
		}
		@JsonView(OpportunityVo.Detail.class)
		public String getPic2() {
			return pic2;
		}
		public void setPic2(String pic2) {
			this.pic2 = pic2;
		}
		@JsonView(OpportunityVo.Detail.class)
		public String getPic3() {
			return pic3;
		}
		public void setPic3(String pic3) {
			this.pic3 = pic3;
		}
		@JsonView(OpportunityVo.Detail.class)
		public String getUrl1() {
			return url1;
		}
		public void setUrl1(String url1) {
			this.url1 = url1;
		}
		@JsonView(OpportunityVo.Detail.class)
		public String getUrl2() {
			return url2;
		}
		public void setUrl2(String url2) {
			this.url2 = url2;
		}
		@JsonView(OpportunityVo.Detail.class)
		public String getUrl3() {
			return url3;
		}
		public void setUrl3(String url3) {
			this.url3 = url3;
		}
		@JsonView(OpportunityVo.Detail.class)
		public String getFinishTime() {
			return finishTime;
		}
		public void setFinishTime(String finishTime) {
			this.finishTime = finishTime;
		}
		@JsonView(OpportunityVo.Detail.class)
		public float getPrice() {
			return price;
		}
		public void setPrice(float price) {
			this.price = price;
		}
		
		
	    
	    
}
