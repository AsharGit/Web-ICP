let hand;
let computer;
let player;
let rock = document.getElementById('rock')
let paper = document.getElementById('paper')
let scissors = document.getElementById('scissors')
let message = document.getElementById("message")

// Generate result
function game(playerHand, computerHand) {
    if (playerHand === computerHand) {
        message.innerHTML = 'You chose: ' + player + ', Computer chose: ' + computer + ', The result is a tie';
    } else if (playerHand === 'Rock' && computerHand === 'Paper') {
        message.innerHTML = 'You chose: ' + player + ', Computer chose: ' + computer + ', You lose!';
    } else if (playerHand === 'Rock' && computerHand === 'Scissors') {
        message.innerHTML = 'You chose: ' + player + ', Computer chose: ' + computer + ', You win!';
    } else if (playerHand === 'Paper' && computerHand === 'Rock') {
        message.innerHTML = 'You chose: ' + player + ', Computer chose: ' + computer + ', You win!';
    } else if (playerHand === 'Paper' && computerHand === 'Scissors') {
        message.innerHTML = 'You chose: ' + player + ', Computer chose: ' + computer + ', You lose!';
    } else if (playerHand === 'Scissors' && computerHand === 'Rock') {
        message.innerHTML = 'You chose: ' + player + ', Computer chose: ' + computer + ', You lose!';
    } else if (playerHand === 'Scissors' && computerHand === 'Paper') {
        message.innerHTML = 'You chose: ' + player + ', Computer chose: ' + computer + ', You win!';
    }

}

// Generate computer choice
function compChoice() {
    hand = Math.floor(Math.random() * 3)

    switch (hand) {
        case 0:
            computer = 'Rock';
            break;
        case 1:
            computer = 'Paper';
            break;
        case 2:
            computer = 'Scissors'
            break;
    }

}

// Print the result of player choice and computer choice
function playerChoice(choice) {
    compChoice();
    player = choice;
    game(player, computer);
}

// Listen to user clicks
rock.addEventListener('click', function () {
    playerChoice('Rock')
})

paper.addEventListener('click', function () {
    playerChoice('Paper')
})

scissors.addEventListener('click', function () {
    playerChoice('Scissors')
})



