Entities
----------------------------------------
An entity represents a concept in your domain that is defined by its identity rather than its
attributes. Although an entity’s identity remains fixed throughout its lifecycle, its attributes may
change. An entity is responsible for defining what it means to be the same; in code this is often
achieved by overriding the equality operations of a class.

Building blocks:
 - Factory
   Use factory to create complex entities/value objects/aggregates
 - Application services
   orchestrate only, they contain no business logic


Value Objects
----------------------------------------
Value objects represent the elements or concepts of your domain that are known only by their
characteristics; they are used as descriptors for elements in your model; they do not require a
unique identity. Because value objects have no conceptual identity within the model, they are
defined by their attributes; their attributes determine their identity.
For instance, you may have an order entity that uses value objects
to represent the order shipping address, items, courier information, and so on. Not one of these
characteristics needs identity itself because it only has meaning within the context of being
attached to an order.

Domain Services
----------------------------------------
Domain services encapsulate domain logic and concepts that are not naturally modeled as value
objects or entities in your model. Domain services have no identity or state; their responsibility is
to orchestrate business logic using entities and value objects. A good example of a domain service
is a shipping cost calculator
This service is a business function that, given
a set of consignments (value objects) and a collection of weight bandings, can calculate the cost of
shipping. This functionality does not sit comfortably on a domain object, so it is better represented
as a domain service.

Aggregates
----------------------------------------
https://dzone.com/articles/aggregate-pattern