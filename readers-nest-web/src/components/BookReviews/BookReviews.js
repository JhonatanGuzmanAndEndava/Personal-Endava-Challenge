import React, { Component } from 'react';
import PropTypes from 'prop-types';
import './BookReviews.css';

class BookReviews extends Component {
  reviewsToList() {
    return this.props.reviews.map(review => (
      <div className="comment" key={review.author.id}>
        <div className="content">
          <span className="author">{review.author.username}</span>
          <div className="metadata">
            <span className="date">{review.postedDate}</span>
          </div>
          <div className="text">
            {review.content}
          </div>
        </div>
      </div>
    ));
  }
  render() {
    return (
      <div className="ui comments fluid">
        <h3 className="ui dividing header">Reviews</h3>
        {this.reviewsToList()}
        <form className="ui reply form">
          <div className="field">
            <textarea placeholder="Type a message" />
          </div>
          <button className="ui blue labeled submit icon button">
            <i className="icon edit" /> Add Review
          </button>
        </form>
      </div>
    );
  }
}

BookReviews.propTypes = {
  reviews: PropTypes.arrayOf(PropTypes.shape({
    author: PropTypes.shape({
      id: PropTypes.string,
      username: PropTypes.string,
    }),
    content: PropTypes.string,
    postedDate: PropTypes.string,
  })).isRequired,
};

export default BookReviews;
