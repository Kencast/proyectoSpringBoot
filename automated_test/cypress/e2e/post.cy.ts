import 'cypress-xpath';

function login(){
  cy.get('#userName').type('login@prueba.com');
  cy.get('#pwd').type('login123');
  cy.xpath('/html/body/div/form/button').click();
}

function logout(){
  cy.xpath('/html/body/div[1]/nav/div/ul/li[3]/a').click();
  cy.xpath('/html/body/div[2]/div/div[1]/div/div/div[2]/div[5]/form/button').click();
}

describe('Posts tests with session', function() {

  before(function() {
    cy.visit('localhost:8081/login');
    login();
  });

  beforeEach(function() {
    cy.visit('localhost:8081/home');
  });

  after(function() {
    cy.clearCookies()
    cy.clearLocalStorage()
    cy.window().then((win) => {
      win.sessionStorage.clear()
    })
    logout();
    cy.reload();
    cy.visit('localhost:8081/home');
  });

  it('021 - Create Post without title', () => {
    cy.xpath('/html/body/div[2]/div[3]/div/div/div/a'). click();
    // cy.get('#title').type('Don’t understand my problems');
    // cy.xpath('/html/body/div[2]/div[2]/form/div[2]/div/div[2]/div').type('Today was a very bad day for me :(');
    cy.xpath('/html/body/div[2]/div[2]/form/div[3]/div/div/span/span[1]/span/span/input').type('python ');
    cy.xpath('/html/body/div[2]/div[2]/form/button').click();
    cy.contains('The title cannot be empty');
  });

  it('022 - Create Post without content',() =>{
    cy.xpath('/html/body/div[2]/div[3]/div/div/div/a'). click();
    cy.get('#title').type('I need help');
    cy.xpath('/html/body/div[2]/div[2]/form/div[3]/div/div/span/span[1]/span/span/input').type('dynamic_programming ');
    cy.xpath('/html/body/div[2]/div[2]/form/button').click();
    cy.contains('The content cannot be empty');
  });

  it('025 - Enter a Post not been the owner' , () => {
    cy.contains('a', 'NeetCode').click();
    cy.contains('Comments');
    cy.contains('Add a comment');
    cy.xpath('/html/body/div[2]/div[3]/div/div/div[1]/form/button').should('not.exist');
    cy.xpath('/html/body/div[2]/div[3]/div/div/div[2]/form/button').should('not.exist');
  });

  it('026 - Create Comment', () => {
    cy.contains('a', 'NeetCode').click();
    cy.get('#parr').type('Can you give an example? Please');
    cy.xpath('/html/body/div[2]/div[2]/div/div[2]/form/button').click();
    cy.contains('Can you give an example? Please');
  });

  it('027 - Create a empty comment', () => {
    cy.contains('a', 'NeetCode').click();
    cy.xpath('/html/body/div[2]/div[2]/div/div[2]/form/button').click();
    cy.contains('The description cannot be empty');
  });

  it('058 - Enter someone else profile', () => {
    cy.contains('a', 'steve').click();
    cy.contains('steve');
    cy.contains('Your Profile');
  });

  it('061 - View other user posts', () => {
    cy.contains('a', 'steve').click();
    cy.xpath('/html/body/div[2]/div/div[1]/div/div/div[2]/div[2]/button/a').click();
    cy.contains('Posts');
    cy.contains('Uppss....There is nothing to see here').should('not.exist');
  });
})

describe('Post without a session', function () {
  beforeEach(function() {
    cy.visit('localhost:8081/home');
  });

  afterEach(function () {
    cy.clearCookies()
    cy.clearLocalStorage()
    cy.window().then((win) => {
      win.sessionStorage.clear()
    })
    cy.reload();
    cy.visit('localhost:8081/home');
  });

  it('024 - Enter in a Post without login', () => {
    cy.xpath('/html/body/div[2]/div[2]/div/div/div/div[1]/div/div/div[2]/div/div[1]/a').click();
    cy.contains('Comments');
    cy.contains('Add a comment').should('not.exist');
  });

  it('040 - Error when creating post', () => {
    cy.xpath('/html/body/div[2]/div[3]/div/div/div/a').click();
    cy.contains('Login');
  });

});

describe('Modify Post', function () {
  before(function (){
    cy.visit('localhost:8081/login');
    login();
  });

  beforeEach(function (){
    cy.xpath('/html/body/div[1]/nav/div/ul/li[3]/a').click();
    cy.xpath('/html/body/div[2]/div/div[1]/div/div/div[2]/div[2]/button').click();
  });

  after(function() {
    cy.clearCookies()
    cy.clearLocalStorage()
    cy.window().then((win) => {
      win.sessionStorage.clear()
    })
    cy.reload();
    logout();
    cy.visit('localhost:8081/home');
  });

  it('028 - Modify the Post Title', () => {
    cy.xpath('/html/body/div[2]/div[2]/div/div/div/div/div/div/div[2]/div/div[1]/a').click();
    cy.xpath('/html/body/div[2]/div[3]/div/div/div[1]/form/button').click();
    cy.get('#title').clear().type('THIS DON’T MAKE ANY SENSE');
    cy.xpath('/html/body/div[2]/div[2]/form/button').click();
    cy.contains('THIS DON’T MAKE ANY SENSE');
  });

  it('030 - Modify the Post Title to make it empty', () => {
    cy.xpath('/html/body/div[2]/div[2]/div/div/div/div/div/div/div[2]/div/div[1]/a').click();
    cy.xpath('/html/body/div[2]/div[3]/div/div/div[1]/form/button').click();
    cy.get('#title').clear();
    cy.xpath('/html/body/div[2]/div[2]/form/button').click();
    cy.contains('The title cannot be empty');
  });

  it('045 - Enter a post being the owner', () => {
    cy.xpath('/html/body/div[2]/div[2]/div/div/div/div/div/div/div[2]/div/div[1]/a').click();
    cy.contains('Comments');
    cy.contains('Add a comment');
    cy.xpath('/html/body/div[2]/div[3]/div/div/div[1]/form/button').should('exist');
    cy.xpath('/html/body/div[2]/div[3]/div/div/div[2]/form/button').should('exist');
  });

});
