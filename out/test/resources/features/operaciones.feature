Feature: Operaciones
  As usuario
  I want sumar numeros

  Background:
    Given que "usuario" quiere hacer operaciones con dos numeros

  Scenario: restar dos numeros
    When restar dos numeros
    Then la api respondera con el resultado

  Scenario: Multiplicar dos numeros
    When multiplica los numeros
    Then la api respondera el resultado de la multiplicacion