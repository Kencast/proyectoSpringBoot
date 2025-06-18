import 'cypress-xpath';

function deleteUser() {
  cy.xpath('/html/body/div[1]/nav/div/ul/li[3]/a').click();
  cy.xpath('/html/body/div[2]/div/div[1]/div/div/div[2]/div[4]/form/button').click();
}

describe('Register', function() {
  beforeEach(function() {
    cy.visit('localhost:8081/login');
  });

  afterEach(function() {
    cy.clearCookies()
    cy.clearLocalStorage()
    cy.window().then((win) => {
      win.sessionStorage.clear()
    })
    cy.reload();
  });

  it('001-Successful register of a user', () => {
    cy.xpath('/html/body/div/div[3]/a').click();
    cy.get('#name').type('Kevin');
    cy.get('#firstLastName').type('Ramirez');
    cy.get('#secondLastName').type('Latysh');
    cy.get('#userName').type('KLR');
    cy.get('#country').type('Nigeria');
    cy.get('#birthdate').invoke('val','2008-07-04').trigger('change');
    cy.get('#email').type('kevrala@gmail.com');
    cy.get('#pwd').type('123kev321');
    cy.xpath('/html/body/div/form/button').click();
    cy.contains('Posts');
    deleteUser();
  });

  it('009 -Register without name', () => {
    cy.xpath('/html/body/div/div[3]/a').click();
    cy.get('#firstLastName').type('Korotkevich');
    cy.get('#secondLastName').type('Bolt');
    cy.get('#userName').type('Who_is_Tourit');
    cy.get('#country').type('Canada');
    cy.get('#birthdate').invoke('val','1992-02-10').trigger('change');
    cy.get('#email').type('kobo@gmail.com');
    cy.get('#pwd').type('456ho654');
    cy.xpath('/html/body/div/form/button').click();
    cy.contains('The name cannot be empty');
  });

  it('010 -Register without first name', () => {
    cy.xpath('/html/body/div/div[3]/a').click();
    cy.get('#name').type('Gennady');
    // cy.get('#firstLastName').type('Korotkevich');
    cy.get('#secondLastName').type('Pajor');
    cy.get('#userName').type('Ivo');
    cy.get('#country').type('United States');
    cy.get('#birthdate').invoke('val','1993-01-12').trigger('change');
    cy.get('#email').type('geiv@gmail.com');
    cy.get('#pwd').type(' 091324543');
    cy.xpath('/html/body/div/form/button').click();
    cy.contains('The first name cannot be empty');
  });

  it('011 -Register without second name', () => {
    cy.xpath('/html/body/div/div[3]/a').click();
    cy.get('#name').type('Carlos');
    cy.get('#firstLastName').type('Miguel');
    // cy.get('#secondLastName').type('Bolt');
    cy.get('#userName').type('Soto');
    cy.get('#country').type('Mexico');
    cy.get('#birthdate').invoke('val','1994-12-13').trigger('change');
    cy.get('#email').type('camki@gmail.com');
    cy.get('#pwd').type('214633213');
    cy.xpath('/html/body/div/form/button').click();
    cy.contains('The second name cannot be empty');
  });

  it('013 -Register without username', () => {
    cy.xpath('/html/body/div/div[3]/a').click();
    cy.get('#name').type('Agustin');
    cy.get('#firstLastName').type('Gutiérrez');
    cy.get('#secondLastName').type('Prim');
    cy.get('#country').type('Belice');
    cy.get('#birthdate').invoke('val','1995-11-14').trigger('change');
    cy.get('#email').type('aggupr@gmail.com');
    cy.get('#pwd').type('758093212');
    cy.xpath('/html/body/div/form/button').click();
    cy.contains('The user name cannot be empty');
  });

  it('014 - Register with a birthdate after the current one', () => {
    cy.xpath('/html/body/div/div[3]/a').click();
    cy.get('#name').type('Pedro');
    cy.get('#firstLastName').type('Pérez');
    cy.get('#secondLastName').type('Rodríguez');
    cy.get('#userName').type('Papulon');
    cy.get('#country').type('China');
    cy.get('#birthdate').invoke('val','2026-05-06').trigger('change');
    cy.get('#email').type('papulon@china.cr');
    cy.get('#pwd').type('12345678');
    cy.xpath('/html/body/div/form/button').click();
    cy.contains('Errors detected');
  });

  it('015 - Register without country', () => {
    cy.xpath('/html/body/div/div[3]/a').click();
    cy.get('#name').type('Keylor');
    cy.get('#firstLastName').type('Navas');
    cy.get('#secondLastName').type('Mbappe');
    cy.get('#userName').type('jiangly');
    //cy.get('#country').type('');
    cy.get('#birthdate').invoke('val','1996-10-15').trigger('change');
    cy.get('#email').type('kenamb@gmail.com');
    cy.get('#pwd').type('nc89b82c37');
    cy.xpath('/html/body/div/form/button').click();
    cy.contains('The country cannot be empty');
  });

  it('016 - Register without birthdate', () => {
    cy.xpath('/html/body/div/div[3]/a').click();
    cy.get('#name').type('Kyliam');
    cy.get('#firstLastName').type('Fernandez');
    cy.get('#secondLastName').type('Arce');
    cy.get('#userName').type('orzdevinwang');
    cy.get('#country').type('El Salvador');
    cy.get('#email').type('kyfear@gmail.com');
    cy.get('#pwd').type('9ju03r2cc3089hur');
    cy.xpath('/html/body/div/form/button').click();
    cy.contains('There was an error, please check your data and try again');
  });

  it('017 - Register without an Email', () => {
    cy.xpath('/html/body/div/div[3]/a').click();
    cy.get('#name').type('Thibut');
    cy.get('#firstLastName').type('Courthois');
    cy.get('#secondLastName').type('Vargas');
    cy.get('#userName').type('maroonrk');
    cy.get('#country').type('Honduras');
    cy.get('#birthdate').invoke('val','2005-04-05').trigger('change');
    // cy.get('#email').type('papulon@china.cr');
    cy.get('#pwd').type('9ju03r2cc3089hur');
    cy.xpath('/html/body/div/form/button').click();
    cy.contains('The email cannot be empty');
  });

  it('018 - Register without password', () => {
    cy.xpath('/html/body/div/div[3]/a').click();
    cy.get('#name').type('Francisco');
    cy.get('#firstLastName').type('Hernandez');
    cy.get('#secondLastName').type('Li');
    cy.get('#userName').type('jqdai0815');
    cy.get('#country').type('Guatemala');
    cy.get('#birthdate').invoke('val','1993-06-08').trigger('change');
    cy.get('#email').type('franli@hotmail.cn');
    //cy.get('#pwd').type('12345678');
    cy.xpath('/html/body/div/form/button').click();
    cy.contains('The password cannot be empty');
  });

});
