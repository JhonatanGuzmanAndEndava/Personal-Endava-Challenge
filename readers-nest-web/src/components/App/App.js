import React from 'react';
import { Route, Switch } from 'react-router-dom';

import './App.css';
import Navbar from '../Navbar/Navbar';
import MainBanner from '../MainBanner/MainBanner';
import BooksPage from '../../pages/BooksPage';
import BookclubsPage from '../../pages/BookclubsPage';
import BookInfoPage from '../../pages/BookInfoPage';
import BookclubInfoPage from '../../pages/BookclubInfoPage';
import Login from '../Login/Login';
import Signup from '../Signup/Signup';
import CreateBookclub from '../CreateBookclub/CreateBookclub';

const App = () => (
  <div className="ui vertical masthead segment">
    <Navbar />
    <Switch>
      <Route exact path="/" component={MainBanner} />
      <Route exact path="/books" component={BooksPage} />
      <Route exact path="/bookclubs" component={BookclubsPage} />
      <Route exact path="/books/:id" component={BookInfoPage} />
      <Route exact path="/bookclubs/:id" component={BookclubInfoPage} />
      <Route exact path="/login" component={Login} />
      <Route exact path="/signup" component={Signup} />
      <Route exact path="/new/bookclub" component={CreateBookclub} />
    </Switch>
  </div>
);

export default App;
