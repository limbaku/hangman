function newGame() {
  $.ajax({
    type: "POST",
    url: "http://localhost:8080/round/createRound",
  }).done(function(data) {
    array = data.output.join("")
    $('.hiddenWord').text(array);
    $('.gameId').text(data.roundId);
    $('.remaining').text(data.intNumLives.toString())
  }).fail(function(data) {
    console.log(data)
  });
}

function guess(gameId, letter) {
  $.ajax({
    contentType : 'application/json',
    type: "PUT",
    dataType: 'json',
    url: "http://localhost:8080/round/verifyLetter/" + gameId,
    data: JSON.stringify({ "letter":letter}),
    beforeSend: function() {
      $(".letter").prop('disabled', true);
    }
  }).done(function(data) {
    word = data.output.join("")
    attempts = data.lettersUsed.join(",")
    $('.hiddenWord').text(word);
    $('.gameId').text(data.gameId);
    $('.remaining').text(data.intNumLives.toString())
    $('.attempts').text(attempts)
    $('.message').text(data.message);

    $(".letter").prop('disabled', false);
  }).fail(function(data) {
    console.log(data)
  });
}
//
//function getSolution(token) {
//  $.ajax({
//    type: "GET",
//    dataType: 'json',
//    url: "http://hangman-api.herokuapp.com/hangman",
//    data: { "token": token },
//  }).done(function(data) {
//    var hangman_word = $('.hangman-word').text();
//    var solution = data.solution;
//
//    for (var i = solution.length-1; i >= 0; i--) {
//      if (hangman_word.charAt(i) != solution.charAt(i)) {
//        error_string = "<span class='error'>"+ solution.charAt(i) + "</span>";
//        updated_word = hangman_word
//        hangman_word = updated_word.substr(0, i) + error_string + updated_word.substr(i+1);
//      } else {
//        if (hangman_word.indexOf("_") == -1) {
//          $('.console').hide();
//        }
//      }
//    }
//    $('.hangman-word').html(hangman_word);
//  }).fail(function(data) {
//    console.log(data)
//  });
//}
//
//function handleFailure(failures){
//  if (failures == 7) {
//    var token = $('.token').text();
//    getSolution(token);
//    endGame();
//  } else {
//    wrongAnswer(failures);
//  }
//}
//
//function endGame() {
//  $('.remaining-guesses').hide();
//  $('.console').slideToggle(1200);
//    $("#new-game").show();
//}
//

$(document).ready(function(){
  $('.console').hide();
  $('.remaining-guesses').hide();

  $(document).on('click', '#new-game', function(e){
    $(this).hide();
    $('.attempts').empty();
    $('.remaining-guesses').show();
    $('.attempts').show();
    $('.message').show();

    newGame();
    $('.console').slideToggle(1200);
    $('.letter').focus();
  })

  $(document).on('click', '#guess', function(e){
    gameId = $('.gameId').text();
    letter = $('.letter').val();

    $('.letter').focus();
    $('.letter').val('');

    guess(gameId, letter);
  })
});