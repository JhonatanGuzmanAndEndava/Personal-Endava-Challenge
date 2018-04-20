import React, { Component } from 'react';
import PropTypes from 'prop-types';

import gql from 'graphql-tag';
import { graphql } from 'react-apollo';

import { withRouter } from 'react-router-dom';

class CreateBookclub extends Component {
  constructor(props) {
    super(props);
    this.state = {
      name: '',
      description: '',
      isPrivate: false,
    };

    this.onChangeCheckbox = this.onChangeCheckbox.bind(this);
    this.onChange = this.onChange.bind(this);
    this.onCreateBookclub = this.onCreateBookclub.bind(this);
  }

  onChange(event) {
    event.preventDefault();
    this.setState({
      [event.target.name]: event.target.value,
    });
  }

  onChangeCheckbox(event) {
    this.setState({
      [event.target.name]: event.target.checked,
    });
  }

  onCreateBookclub() {
    this.props.createBookclub({
      variables: {
        newBookclub: this.state,
      },
    }).then(({ data }) => {
      this.props.history.replace(`/bookclubs/${data.createBookclub.id}`);
    }).catch(error => (
      console.log(error)
    ));
  }

  render() {
    return (
      <div className="ui container form">
        <h1 className="header">Sign Up</h1>
        <div className="field">
          <label>Name</label>
          <input type="text" name="name" placeholder="Your bookclubs name..." value={this.state.name} onChange={this.onChange} />
        </div>
        <div className="field">
          <label>Description</label>
          <textarea name="description" placeholder="Describe your bookclub here..." value={this.state.description} onChange={this.onChange} />
        </div>
        <div className="field">
          <div className="ui toggle checkbox">
            <input type="checkbox" name="isPrivate" tabIndex="0" onClick={this.onChangeCheckbox} checked={this.state.isPrivate} />
            <label>{this.state.isPrivate ? 'Private' : 'Public'}</label>
          </div>
        </div>
        <button className="fluid ui primary button" onClick={this.onCreateBookclub}>Create Bookclub</button>
      </div>
    );
  }
}

CreateBookclub.propTypes = {
  createBookclub: PropTypes.func.isRequired,
  history: PropTypes.shape({
    replace: PropTypes.func,
  }).isRequired,
};

const CREATE_BOOKCLUB = gql`
  mutation createBookclub($newBookclub:BookclubInput!){
    createBookclub(newBookclub:$newBookclub) {
      id
    }
  }
`;

export default graphql(CREATE_BOOKCLUB, {
  name: 'createBookclub',
})(withRouter(CreateBookclub));

