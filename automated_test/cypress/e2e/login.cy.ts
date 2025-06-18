import 'cypress-xpath';

function logout() {
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

describe('Register', function() {
    beforeEach(function() {
        cy.visit('localhost:8081/login');
    });

    afterEach(function() {
        cy.reload();
    });

    it('003-Bad Email Formant in Login',() => {
      cy.get('#userName').type('kevralaarrobagmail.com');
      cy.get('#pwd').type('12345678');
      cy.xpath('/html/body/div/form/button').click();
      cy.contains('Errors detected You must provide a valid email');
    });

    it('004-Succesful login', ()=>{
       cy.get('#userName').type('login@prueba.com');
       cy.get('#pwd').type('login123');
       cy.xpath('/html/body/div/form/button').click();
       cy.contains('Posts');
       logout();
    });

    it('005-Login with short password', () => {
      cy.get('#userName').type('ana@gmail.com');
      cy.get('#pwd').type('corta');
      cy.xpath('/html/body/div/form/button').click();
      cy.contains('There was an error, please check your data and try again');
    });

    it('006 - Fail in login with incorrect password',() => {
      cy.get('#userName').type('fred@gmail.com');
      cy.get('#pwd').type('kevrala05');
      cy.xpath('/html/body/div/form/button').click();
      cy.contains('There was an error, please check your data and try again');
    });

    it('007 - Fail login with wrong email', () => {
      cy.get('#userName').type('noexiste@hotmail.com');
      cy.get('#pwd').type('validpsw');
      cy.xpath('/html/body/div/form/button').click();
      cy.contains('There was an error, please check your data and try again');
    });
})

describe('Reach Login', function() {

  beforeEach(function() {
    cy.visit('localhost:8081/home');
    logoutIfLoggedIn();
    cy.visit('localhost:8081/home');
  });

  afterEach(function() {
    cy.clearCookies()
    cy.clearLocalStorage()
    cy.window().then((win) => {
      win.sessionStorage.clear()
    })
    cy.reload();
    cy.visit('localhost:8081/home');
  });

  it('041 - Reach Login from Home', () => {
    cy.xpath('/html/body/div[1]/nav/div/ul/li[4]/a').click();
    cy.url().should('include','login');
  });
});
