/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ke.co.safaricom.www.entities;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.validation.constraints.NotNull;

/**
 *
 * @author afro
 */

@Entity
@ApiModel(description = "Class representing Movies/Series Entity Body parameters.")
public class Movies implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @ApiModelProperty(notes = "The userId parameter that is not mandatory, as the API will generate it.", required = false)
    private int movieId;
    @NotNull(message = "The title cannot be empty.")
    @ApiModelProperty(notes = "The title parameter is mandatory as it's used to identify a specific movie. "
            , required = true, example = "Untold Stories")
    private String title;
    @NotNull(message = "The description cannot be empty.")
    @ApiModelProperty(notes = "The description parameter is mandatory as it's used to give a movie/series overview. "
            , required = true, example = "Untold Stories is a movies describing/showing stories that are not shown within mainstream media")
    private String description;
    @NotNull(message = "The recommendation cannot be empty.")
    @ApiModelProperty(notes = "The recommendation parameter is mandatory as it's used to give recommendation in regards to a movie/series. "
            , required = true, example = "Definetly recommend.")
    private String recommendation;
    @NotNull(message = "The movieOrSeries cannot be empty.")
    @ApiModelProperty(notes = "The movieOrSeries parameter is mandatory as it's used to differentiate movies (M) and series(S). "
            , required = true, example = "M")
    private String movieOrSeries;
    @NotNull(message = "The rating cannot be empty.")
    @ApiModelProperty(notes = "The rating parameter is mandatory as it's used to show rating levels in regards to a movie/series. "
            , required = true, example = "1")
    private int rating;
    @ApiModelProperty(notes = "The watched parameter is used to show if the review watched the movie/series. "
            , required = true, example = "true.")
    private boolean watched;
    
    @ApiModelProperty(notes = "The deleted parameter is used to show if the movie/series has been deleted by a user. "
            , required = true, example = "true.")
    private boolean deleted;
    
    @ApiModelProperty(notes = "The addedBy parameter is used to show who update the movies or series. "
            , required = true, example = "254721506974.")
    @Column(updatable = false)
    private String addedBy;
    
    @ApiModelProperty(notes = "The updatedBy parameter is used to show who update the movies or series. "
            , required = true, example = "254721506974.")
    private String updatedBy;
    
    @ApiModelProperty(notes = "The deletedBy parameter is used to show who deleted the movies or series. "
            , required = true, example = "254721506974.")
    private String deletedBy;
    
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    @Column(updatable = false)
    private Date addDate;
    
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date updatedDate;
    
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date deleteDate;

    public Movies() {
        this.addDate = new Date();
        this.rating = 0;
    }
    
    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRecommendation() {
        return recommendation;
    }

    public void setRecommendation(String recommendation) {
        this.recommendation = recommendation;
    }

    public String getMovieOrSeries() {
        return movieOrSeries;
    }

    public void setMovieOrSeries(String movieOrSeries) {
        this.movieOrSeries = movieOrSeries;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public boolean isWatched() {
        return watched;
    }

    public void setWatched(boolean watched) {
        this.watched = watched;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public String getAddedBy() {
        return addedBy;
    }

    public void setAddedBy(String addedBy) {
        this.addedBy = addedBy;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public String getDeletedBy() {
        return deletedBy;
    }

    public void setDeletedBy(String deletedBy) {
        this.deletedBy = deletedBy;
    }

    public Date getAddDate() {
        return addDate;
    }

    public void setAddDate(Date addDate) {
        this.addDate = addDate;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public Date getDeleteDate() {
        return deleteDate;
    }

    public void setDeleteDate(Date deleteDate) {
        this.deleteDate = deleteDate;
    }
    
    
}
