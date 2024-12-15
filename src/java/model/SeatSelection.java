package model;

public class SeatSelection {
    private int selectionId;
    private int nowShowingId;
    private String showtime;
    private String seatNumber;

    // Constructor
    public SeatSelection(int nowShowingId, String showtime, String seatNumber) {
        this.nowShowingId = nowShowingId;
        this.showtime = showtime;
        this.seatNumber = seatNumber;
    }

    // Getters and Setters
    public int getSelectionId() {
        return selectionId;
    }

    public void setSelectionId(int selectionId) {
        this.selectionId = selectionId;
    }

    public int getNowShowingId() {
        return nowShowingId;
    }

    public void setNowShowingId(int nowShowingId) {
        this.nowShowingId = nowShowingId;
    }

    public String getShowtime() {
        return showtime;
    }

    public void setShowtime(String showtime) {
        this.showtime = showtime;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
    }
}
