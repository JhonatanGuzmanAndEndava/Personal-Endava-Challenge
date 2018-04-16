export const Schema = [`
    
    input UserCreateInput {
        firstName: String!
        lastName:String!
        username:String!
        email:String!
        password:String!
    }

    input UserUpdateInput {
        firstName: String
        lastName:String
        username:String
        email:String
        currentBookId:String
    }

    input UserCredentialsInput {
        email:String!
        password:String!
    }

    type User {
        id: ID!
        firstName: String!
        lastName:String!
        username:String!
        email:String!

        bookHistory:[Book!]!
        currentBook:Book
    }

    type AuthPayload {
        userId:ID!
        token:String!
        refreshToken:String!
    }

    extend type Query {

        user(id:ID!):User!
        users:[User!]!
    }

    extend type Mutation {

        login(credentials:UserCredentialsInput!):AuthPayload!
        signup(newUser:UserCreateInput!):AuthPayload!
        refreshToken(refreshToken:String!):String!

        updateUser(id:ID!, userPayload: UserUpdateInput):User!
        deleteUser(id:ID!):String!

        addBookToUserHistory(userId:ID!, bookId:ID!):String!
    }
`];

export const Resolvers = {
  Query: {
    user(root, { id }, context) {
      return context.Users.findById(id);
    },
    users(root, args, context) {
      return context.Users.findAll();
    },
  },
  Mutation: {
    login(root, { credentials }, context) {
      return context.Users.login(credentials);
    },
    signup(root, { newUser }, context) {
      return context.Users.signup(newUser);
    },
    refreshToken(root, { refreshToken }, context) {
      return context.Users.refreshToken(refreshToken);
    },
    updateUser(root, { id, userPayload }, context) {
      return context.Users.updateUser(id, userPayload);
    },
    deleteUser(root, { id }, context) {
      return context.Users.deleteUser(id);
    },
    addBookToUserHistory(root, { userId, bookId }, context) {
      return context.Users.addBookToUserHistory(userId, bookId);
    },
  },
  User: {
    bookHistory(user, _, context) {
      return user.bookHistory.map(bookId => context.Books.findById(bookId));
    },
    currentBook(user, _, context) {
      if (user.currentBookId) {
        return context.Books.findById(user.currentBookId);
      }
      return null;
    },
  },
};
