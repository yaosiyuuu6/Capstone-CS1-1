package com.example.Neighborhood_Walk.model;

public class MessageDetails {
    private String messageBody;
    private int listId;
    private String sendingSource;
    private String smsCampaignName;

    public MessageDetails() {
    }

    public String getMessageBody() {
        return messageBody;
    }

    public void setMessageBody(String messageBody) {
        this.messageBody = messageBody;
    }

    public int getListId() {
        return listId;
    }

    public void setListId(int listId) {
        this.listId = listId;
    }

    public String getSendingSource() {
        return sendingSource;
    }

    public void setSendingSource(String sendingSource) {
        this.sendingSource = sendingSource;
    }

    public String getSmsCampaignName() {
        return smsCampaignName;
    }

    public void setSmsCampaignName(String smsCampaignName) {
        this.smsCampaignName = smsCampaignName;
    }
}
