import React, { Component } from 'react';
import PropTypes from 'prop-types';

import gql from 'graphql-tag';
import { graphql } from 'react-apollo';


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

    this.state = {
      newMessage: '',
    };

    this.createMessage = this.createMessage.bind(this);
    this.onChangeNewMessage = this.onChangeNewMessage.bind(this);
  }

  onChangeNewMessage(event) {
    this.setState({
      newMessage: event.target.value,
    });
  }

  createMessage() {
    this.props.postMessage({
      variables: {
        id: this.props.bookclubId,
        messageContent: this.state.newMessage,
      },
    }).catch(error => (
      console.log(error)
    ));
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
            <textarea rows="2" placeholder="Type a message" value={this.state.newMessage} onChange={this.onChangeNewMessage}/>
          </div>
          <button className="ui blue labeled submit icon button" onClick={this.createMessage}>
            <i className="icon edit" /> Send Message
          </button>
        </form>
      </div>
    );
  }
}

MessageList.propTypes = {
  bookclubId: PropTypes.string.isRequired,
  messages: PropTypes.arrayOf(PropTypes.shape({
    author: PropTypes.shape({
      id: PropTypes.string,
      username: PropTypes.string,
    }).isRequired,
    contentMessage: PropTypes.string.isRequired,
    publishedDate: PropTypes.string.isRequired,
  })).isRequired,
  postMessage: PropTypes.func.isRequired,
};

const POST_MESSAGE = gql`
  mutation postMessage($id:ID!, $messageContent:String!){
    postMessageToBookclub(bookclubId:$id, messageContent:$messageContent) {
      contentMessage
      author {
        username
      }
      publishedDate
    }
  }
`;

export default graphql(POST_MESSAGE, {
  name: 'postMessage',
})(MessageList);

