function basics() {
    console.log("Hello!");

//var message = "hello js"; var is the old keyword
//const is a constant value; cannot be changed later
    const school = "BTS";
    let name = "Monica";

//there are two ways of defining the message:
    console.log("I'm " + name + " and I'm in " + school);
    console.log(`I'm ${name} and I'm in ${school}`);

    let age = 20;

    if (age >= 10) {
        console.log("adult");
    } else {
        console.log("not adult");
    }
}

function functions() {
    let result = sum(4, 6);
    console.log("The result is " + result);
    greet("BTS class");

//this function does not return anything so if we put it in a variable (let) it will be "undefined"
    function greet(name) {
        console.log("Hello" + name);
    }

    function sum(x, y) {
        return x + y;
    }

    /** Functions, anonymous functions and arrow functions */
    function sayHello(name) {               //normal function
        console.log("Hello " + name);
    }

    let sayHi = function (name) {           //anonymous function
        console.log("Hi " + name);
    };

    let sayBye = (name) => {                //arrow function
        console.log("bye " + name);
    };

    //They're all called the same way
    sayHello("class");
    sayHi("again");
    sayBye("people");

    //We can do the same action with less code needed
    let sum1 = function (a, b) {
        return a + b;
    };

    let sum2 = (a, b) => a + b;

    console.log("sum: " + sum1(2, 3));
    console.log("sum: " + sum2(2, 3));
}

function arrays() {
//in difference with arrays in Java, in JS arrays don't have a predefined size, so we can add values after creating it
    let array = [2, 4, 6];
    let array2 = []; //empty array
    array.push(8);
    let x = array[1];
    console.log("x = " + x);

    for (let i = 0; i < array.length; i++) {
        console.log("Value at index " + i + " is " + array[i]);
    }

    for (let value of array) {
        console.log(value);
    }
}

function loops() {
    let i = 1;
    while (i <= 5) {
        console.log(i);
        i++;
    }

    for (let j = 1; j <= 5; j++) {
        console.log(j);
    }

}

function objects() {
    let car1 = {
        name: "Ferrari",
        color: "red",
        maxSpeed: 300,
        available: true
    };

    let car2 = {
        name: "Ford",
        maxSpeed: 200,
        available: false,
        weight: 1000
    };

//there are two ways to get the properties of an object
    console.log(car1.name + " has color " + car1.color);
    console.log(car1["name"] + " has color " + car1["color"]);

//it is possible to create two objects with different properties, but then we have to be aware of not asking for
//a property the object does not have
    console.log(car2.name + " has color " + car2.color); //color will be undefined

//we can add properties after creating the objects
    car2.owner = "Ayça";
    console.log(car2.name + " belongs to " + car2.owner);


    let property = "color";
    console.log("The property " + property + " of car 1 is " + car1[property]);
}

function classes() {
    class Car {
        //in JS, classes are not much used, it is simpler to create objects. But if the code is more sophisticated (long)
        //we can organize it in classes

        //we don't declare the fields but we generate the constructor with the necessary parameters
        constructor(name, maxSpeed) {
            this.name = name;
            this.maxSpeed = maxSpeed;
            this.speed = 0;
        }

        //in Java "this." is optional but in JS it is necessary to access the fields due to they are not declared before
        accelerate(amount) {
            this.speed += amount;
        }
    }

//object from a class
    let car = new Car("Audi", 250);
    car.accelerate(50);
    car.accelerate(20);
    console.log("Now the " + car.name + " is running at " + car.speed);

//object without class
    let car2 = {
        name: "Opel",
        speed: 100
    };
    console.log("Now the " + car2.name + " is running at " + car2.speed);

    console.log(car);
    console.log(car2);
}

let button = document.getElementById("button");
button.onclick = function () {
    displayCards();
};


function displayCards() {
    let text = document.getElementById("text");
    text.textContent = "Let's play...";

    let container = document.getElementById("container");

    let subtitle = document.createElement("h3");
    subtitle.textContent = "...the card game!!";
    container.appendChild(subtitle);

    let cards = [
        {magic: 1, strength: 8, intelligence: 1},
        {magic: 2, strength: 7, intelligence: 1},
        {magic: 3, strength: 3, intelligence: 4}];
    for (let i = 0; i < cards.length; i++) {
        let paragraph = document.createElement("p");
        paragraph.textContent = "Card " + (i + 1) + " - I: " + cards[i].intelligence + " M: " + cards[i].magic + " S: " + cards[i].strength;
        container.appendChild(paragraph);
    }

}
