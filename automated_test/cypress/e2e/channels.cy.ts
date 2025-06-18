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

function logoutIfLoggedIn() {
  cy.get('body').then($body => {
    if ($body.find('nav:contains("Login")').length == 0) {
      logout();
    }
  });
}

describe('Channels without session', function () {
  beforeEach(function() {
    cy.visit('localhost:8081/home');
    logoutIfLoggedIn();
    cy.visit('localhost:8081/home');
  });

  afterEach(function () {
    cy.clearCookies()
    cy.clearLocalStorage()
    cy.window().then((win) => {
      win.sessionStorage.clear()
    })
    cy.reload();
  });

  it('033 - Get Channels', () => {
    cy.xpath('/html/body/div[1]/nav/div/ul/li[2]/a').click();
    cy.contains('Channels');
  });

  it('035 - Create Channel not success', () => {
    cy.xpath('/html/body/div[1]/nav/div/ul/li[2]/a').click();
    cy.xpath('/html/body/div[2]/div[3]/div/div/div/a').click();
    cy.url().should('include','login');
  });

  it('048 - Enter a Channel without a session', () => {
    cy.xpath('/html/body/div[1]/nav/div/ul/li[2]/a').click();
    cy.xpath('/html/body/div[2]/div[2]/div/div/div/div[1]/div/div/div[2]/div/div[1]/a').click();
    cy.contains('Materials');
  })
})

describe('Channels with login', function () {

  before(function() {
    cy.visit('localhost:8081/login');
    login();
  });

  beforeEach(function() {
    cy.visit('localhost:8081/channels');
  });

  after(function() {
    cy.clearCookies()
    cy.clearLocalStorage()
    cy.window().then((win) => {
      win.sessionStorage.clear()
    })
    logout();
    cy.reload();
  });

  it('036 - Create a channel without a title', () => {
    cy.xpath('/html/body/div[2]/div[3]/div/div/div/a').click();
    cy.xpath('/html/body/div[2]/div[2]/form/div[3]/div/div/span/span[1]/span/span/input').type('oci ');
    cy.xpath('/html/body/div[2]/div[2]/form/button').click();
    cy.contains('The title cannot be empty');
  });

  it('037 - Create a channel without a content', () => {
    cy.xpath('/html/body/div[2]/div[3]/div/div/div/a').click();
    cy.get('#title').type('GRAFOS TORNEOS');
    cy.xpath('/html/body/div[2]/div[2]/form/div[3]/div/div/span/span[1]/span/span/input').type('most_important ');
    cy.xpath('/html/body/div[2]/div[2]/form/button').click();
    cy.contains('The content cannot be empty');
  });

  it('046 - Enter a Channel not been the owner' , () => {
    cy.contains('a','Dijkstra with weighted negative edges').click();
    cy.contains('Materials');
    cy.contains('Add a new materials').should('not.exist');
    cy.xpath('/html/body/div[2]/div[3]/div/div/div/form/button').should('not.exist');
  })
})

describe('Channels from profile', function(){

  before(function() {
    cy.visit('localhost:8081/login');
    login();
  });

  beforeEach(function() {
    cy.xpath('/html/body/div[1]/nav/div/ul/li[3]/a').click();
    cy.xpath('/html/body/div[2]/div/div[1]/div/div/div[2]/div[3]/button/a').click();
  });

  after(function() {
    cy.clearCookies()
    cy.clearLocalStorage()
    cy.window().then((win) => {
      win.sessionStorage.clear()
    })
    logout();
    cy.reload();
  });


  it('043 - Create Material with title size less than 3', () => {
    cy.xpath('/html/body/div[2]/div[2]/div/div/div/div/div/div/div[2]/div/div[1]/a').click();
    cy.xpath('/html/body/div[2]/div[2]/div/div[2]/form/button').click();
    cy.contains('The title cannot be empty');
  });

  it('044 - Create Material without content', () => {
    cy.xpath('/html/body/div[2]/div[2]/div/div/div/div/div/div/div[2]/div/div[1]/a').click();
    cy.get('#title').type('GAUSSIAN');
    cy.xpath('/html/body/div[2]/div[2]/div/div[2]/form/button').click();
    cy.contains('The content cannot be empty');
  });

  it('045 - Enter a Channel been the owner' ,() => {
    cy.xpath('/html/body/div[2]/div[2]/div/div/div/div/div/div/div[2]/div/div[1]/a').click();
    cy.contains('Materials');
    cy.contains('Add a new material');
    cy.xpath('/html/body/div[2]/div[3]/div/div/div/form/button').should('exist');
  });

})
