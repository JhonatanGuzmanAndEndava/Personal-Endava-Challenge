import { merge } from 'lodash';

import { Schema as booksSchema, Resolvers as booksResolvers } from './books/schema';
import { Schema as usersSchema, Resolvers as usersResolvers } from './users/schema';
import { Schema as bookclubsSchema, Resolvers as bookclubsResolvers } from './bookclubs/schema';

import Books from './books/models';
import Users from './users/models';
import Bookclubs from './bookclubs/models';

const rootSchema = [`
  type Query {
    _ : Boolean
  }

  type Mutation {
    _ : Boolean
  }

  schema {
    query:Query
    mutation: Mutation
  }
`];

const getHeader = req => ({ header: req.request.header('Authorization') });


export const typeDefs = [...rootSchema, ...booksSchema, ...usersSchema, ...bookclubsSchema];
export const resolvers = merge(booksResolvers, usersResolvers, bookclubsResolvers);
export const context = req => ({
  Users: new Users(getHeader(req)),
  Books: new Books(getHeader(req)),
  bookclubs: new Bookclubs(getHeader(req)),
});
