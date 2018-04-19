import React, { Component } from 'react';
import PropTypes from 'prop-types';

const Message = ({ message }) => (
  <div className="comment">
    <div className="avatar">
      <i className="user circle icon" />
    </div>
    <div className="content">
      <span className="author">{message.author.username}</span>
      <div className="metadata">
        <div className="date">
          {message.publishedDate}
        </div>
      </div>
      <div className="text">
        {message.contentMessage}
      </div>
    </div>
  </div>
);

Message.propTypes = {
  message: PropTypes.shape({
    author: PropTypes.shape({
      id: PropTypes.string,
      username: PropTypes.string,
    }).isRequired,
    contentMessage: PropTypes.string.isRequired,
    publishedDate: PropTypes.string.isRequired,
  }).isRequired,
};

class MessageList extends Component {
  constructor(props) {
    super(props);

    this.createMessage = this.createMessage.bind(this);
  }

  createMessage() {
    console.log(this.props.messages);
  }

  messagesToElems() {
    return this.props.messages.map(message => (
      <Message message={message} key={message.author.id + message.contentMessage} />
    ));
  }

  render() {
    return (
      <div className="ui comments">
        <h3 className="ui dividing header">Messages</h3>
        {this.messagesToElems()}
        <form className="ui form">
          <div className="field">
            <textarea rows="2" placeholder="Type a message" />
          </div>
          <button className="ui blue labeled submit icon button">
            <i className="icon edit" /> Add Reply
          </button>
        </form>
      </div>
    );
  }
}

MessageList.propTypes = {
  messages: PropTypes.arrayOf(PropTypes.shape({
    author: PropTypes.shape({
      id: PropTypes.string,
      username: PropTypes.string,
    }).isRequired,
    contentMessage: PropTypes.string.isRequired,
    publishedDate: PropTypes.string.isRequired,
  })).isRequired,
};

export default MessageList;
