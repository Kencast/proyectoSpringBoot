import 'cypress-xpath';

function login(email:string, pass:string){
  cy.get('#userName').type(email);
  cy.get('#pwd').type(pass);
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

function revertChanges() {
  cy.xpath('/html/body/div[2]/div/div[1]/div/div/div[2]/div[1]/button/a').click();
  cy.get('#name').clear().type('LoginAdmin');
  cy.get('#firstLastName').clear().type('QATesting');
  cy.get('#secondLastName').clear().type('QATesting');
  cy.get('#userName').clear().type('LoginAdmin');
  cy.get('#country').clear().type('Vietnam');
  cy.get('#email').clear().type('login@prueba.com');
  cy.get('#password').clear().type('login123');
  cy.xpath('/html/body/div[2]/form/button').click();
}

describe('Profile with a active session', function () {
  before(function() {
    cy.visit('localhost:8081/login');
    login('login@prueba.com', 'login123');
  });

  beforeEach(function() {
    cy.visit('localhost:8081/home');
    cy.xpath('/html/body/div[1]/nav/div/ul/li[3]/a').click();
  });

  after(function () {
    cy.clearCookies()
    cy.clearLocalStorage()
    cy.window().then((win) => {
      win.sessionStorage.clear()
    })
    logout();
    cy.reload();
  });

  it('057 - Log out', () => {
    cy.xpath('/html/body/div[2]/div/div[1]/div/div/div[2]/div[5]/form/button').click();
    cy.url().should('include','login');
    login("login@prueba.com", "login123");
  })

  it('049 - Visit a Profile with a active session', () => {
    cy.contains('Your Profile');
    cy.contains('Following');
  });

  it('051 - Change a user profile success', () => {
    cy.xpath('/html/body/div[2]/div/div[1]/div/div/div[2]/div[1]/button/a').click();
    cy.get('#name').clear().type('Efrain');
    cy.get('#firstLastName').clear().type('Ramírez');
    cy.get('#secondLastName').clear().type('Latysh');
    cy.get('#userName').clear().type('ERLufff');
    cy.get('#country').clear().type('Panama');
    cy.get('#email').clear().type('elr@gmail.com');
    cy.get('#password').clear().type('jfda098w3213');
    cy.xpath('/html/body/div[2]/form/button').click();
    cy.url().should('include','profile');
    cy.contains('elr@gmail.com');
    revertChanges();
  });

  it('052 - View my posts having posts', () =>{
    cy.xpath('/html/body/div[2]/div/div[1]/div/div/div[2]/div[2]/button').click();
    cy.contains('Posts');
    cy.contains('Uppss....There is nothing to see here').should('not.exist');
  });

  it('054 - View my channels having channels', () => {
    cy.xpath('/html/body/div[2]/div/div[1]/div/div/div[2]/div[3]/button/a').click();
    cy.contains('Channels');
    cy.contains('Uppss....There is nothing to see here').should('not.exist');
  })
});

describe('Profile without an active session', function () {
  before(function() {
    cy.visit('localhost:8081/home');
    logoutIfLoggedIn();
    cy.visit('localhost:8081/home');
  });

  after(function () {
    cy.clearCookies()
    cy.clearLocalStorage()
    cy.window().then((win) => {
      win.sessionStorage.clear()
    })
    cy.reload();
  });

  it('050 - Visit a Profile without a active session', () => {
     cy.xpath('/html/body/div[1]/nav/div/ul/li[3]/a').click();
     cy.url().should('include','login');
  });
});


describe('Profile with session but not content', function (){
  before(function() {
    cy.visit('localhost:8081/login');
    login('no@content.com', 'nocontent');
  });

  beforeEach(function() {
    cy.visit('localhost:8081/home');
    cy.xpath('/html/body/div[1]/nav/div/ul/li[3]/a').click();
  });

  after(function () {
    cy.clearCookies()
    cy.clearLocalStorage()
    cy.window().then((win) => {
      win.sessionStorage.clear()
    })
    logout();
    cy.reload();
  });

  it('053 - Get My Posts no having posts', () => {
    cy.xpath('/html/body/div[2]/div/div[1]/div/div/div[2]/div[2]/button').click();
    cy.contains('Posts');
    cy.contains('Uppss....There is nothing to see here').should('exist');
  });

  it('055 - View my channels no having channels', () => {
    cy.xpath('/html/body/div[2]/div/div[1]/div/div/div[2]/div[3]/button/a').click();
    cy.contains('Channels');
    cy.contains('Uppss....There is nothing to see here').should('exist');
  });

});
