export const Schema = [`

  input BookclubInput {
    name:String!
    description:String!
    actualBookId:ID
    isPrivate:Boolean!
  }
    
  type Bookclub {
    id:ID!
    name:String!
    description:String!
    isPrivate:Boolean!
    actualBook:Book
    messages:[Message!]!
    admins:[User!]!
    members:[User!]!
  }

  type Message {
    contentMessage:String!
    author:User!
    publishedDate:String!
  }

  extend type Query {

    bookclubs:[Bookclub!]!
    bookclub(id:ID!):Bookclub

    bookclubAdmins(bookclubId:ID!):[User!]!
    bookclubMembers(bookclubId:ID!):[User!]!
  }

  extend type Mutation {

    createBookclub(newBookclub:BookclubInput!):Bookclub!
    updateBookclub(id:ID!, newBookclub:BookclubInput!):Bookclub!
    deleteBookclub(id:ID!):ID

    postMessageToBookclub(bookclubId:ID!, messageContent:String!):Message!

    addBookclubMember(bookclubId:ID!, memberId:ID!):ID
    deleteBookclubMember(bookclubId:ID!, memberId:ID!):ID

    addBookclubAdmin(bookclubId:ID!, memberId:ID!):ID
    deleteBookclubAdmin(bookclubId:ID!, memberId:ID!):ID
  }
`];

export const Resolvers = {
  Query: {
    bookclubs(root, args, context) {
      return context.Bookclubs.findAll();
    },
    bookclub(root, { id }, context) {
      return context.Bookclubs.findById(id);
    },
    bookclubAdmins(root, { bookclubId }, context) {
      return context.Bookclubs.getAdmins(bookclubId)
        .then(userIds => userIds.map(id => context.Users.findById(id)));
    },
    bookclubMembers(root, { bookclubId }, context) {
      return context.Bookclubs.getMembers(bookclubId)
        .then(userIds => userIds.map(id => context.Users.findById(id)));
    },
  },
  Mutation: {
    createBookclub(root, { newBookclub }, context) {
      return context.Bookclubs.create(newBookclub);
    },
    updateBookclub(root, { id, newBookclub }, context) {
      return context.Bookclubs.update(id, newBookclub);
    },
    deleteBookclub(root, { id }, context) {
      return context.Bookclubs.delete(id);
    },
    postMessageToBookclub(root, { bookclubId, messageContent }, context) {
      return context.Bookclubs.postMessage(bookclubId, messageContent);
    },
    addBookclubMember(root, { bookclubId, memberId }, context) {
      return context.Bookclubs.addMember(bookclubId, memberId).then(member => member.memberId);
    },
    deleteBookclubMember(root, { bookclubId, memberId }, context) {
      return context.Bookclubs.removeMember(bookclubId, memberId);
    },
    addBookclubAdmin(root, { bookclubId, memberId }, context) {
      return context.Bookclubs.addAdmin(bookclubId, memberId).then(admin => admin.adminId);
    },
    deleteBookclubAdmin(root, { bookclubId, memberId }, context) {
      return context.Bookclubs.removeAdmin(bookclubId, memberId);
    },
  },
  Message: {
    author(message, args, context) {
      return context.Users.findById(message.userId);
    },
  },
  Bookclub: {
    actualBook(bookclub, args, context) {
      if (bookclub.actualBookId) {
        return context.Books.findById(bookclub.actualBookId);
      }
      return null;
    },
    messages(bookclub, args, context) {
      return context.Bookclubs.getMessages(bookclub.id);
    },
    admins(bookclub, args, context) {
      return context.Bookclubs.getAdmins(bookclub.id)
        .then(adminList => adminList.map(id => context.Users.findById(id.adminId)));
    },
    members(bookclub, args, context) {
      return context.Bookclubs.getMembers(bookclub.id)
        .then(memberList => memberList.map(id => context.Users.findById(id.memberId)));
    },
  },
};
