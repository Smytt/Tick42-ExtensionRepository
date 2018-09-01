package com.tick42.quicksilver.models.DTO;

import com.tick42.quicksilver.models.Extension;

import java.util.List;

public class PageDTO<T> {
    private int currentPage;
    private int totalPages;
    private Long totalResults;
    private List<T> extensions;

    PageDTO() {

    }

    public PageDTO(List<T> extensions, int currentPage, int totalPages, Long totalResults) {
        this.setCurrentPage(currentPage);
        this.setTotalResults(totalResults);
        this.setExtensions(extensions);
        this.setTotalPages(totalPages);
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public Long getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(Long totalResults) {
        this.totalResults = totalResults;
    }

    public List<T> getExtensions() {
        return extensions;
    }

    public void setExtensions(List<T> extensions) {
        this.extensions = extensions;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }
}
