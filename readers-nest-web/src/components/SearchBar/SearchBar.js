import React, { Component } from 'react';
import PropTypes from 'prop-types';
import './SearchBar.css';

class SearchBar extends Component {
  constructor(props) {
    super(props);
    this.state = {
      search: 'books',
    };

    this.options = ['Books', 'Bookclubs'];

    this.onChange = this.onChange.bind(this);
    this.onClick = this.onClick.bind(this);
  }


  onChange(event) {
    this.setState({ search: event.target.value.toLowerCase() });
  }

  onClick() {
    this.props.history.push(`/${this.state.search}`);
  }

  getOptions() {
    return this.options.map(option => <option value={option} key={option} >{option}</option>);
  }

  render() {
    return (
      <div className="ui action input">
        <input type="text" placeholder="Search..." />
        <select className="ui compact selection dropdown" defaultValue={this.state.search} onChange={this.onChange} >
          {this.getOptions()}
        </select>
        <button className="ui button" onClick={this.onClick}>Search</button>
      </div>
    );
  }
}

SearchBar.propTypes = {
  history: PropTypes.shape({
    push: PropTypes.func,
  }).isRequired,
};

export default SearchBar;
