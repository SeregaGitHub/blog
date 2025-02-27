'use strict';

let isHandleTagEnabled = false;
const set = new Set();

function handleTagEnabled() {
    isHandleTagEnabled = !isHandleTagEnabled;
}

function handleTag() {
    if (isHandleTagEnabled && window.getSelection) {
        const selection = window.getSelection();
        const index = selection.anchorOffset - 1;
        const char = selection.focusNode.textContent.charAt(index);
        const tag = '#'.concat(selection.focusNode.textContent.substring(selection.anchorOffset, selection.focusOffset).toLowerCase());

        console.log(selection);
        console.log(window);

        console.log(index);
        console.log(char);
        console.log(tag);

        if (set.has(tag)) {
            set.delete(tag);
        } else {
            set.add(tag);
        }

        console.log('===========================');
        for (t of set) {
            console.log(t);
        }
        console.log('===========================');

        console.log('--------------------------------------------------');

      } else {
        console.log('===========================');
        for (t of set) {
            console.log(t);
        }
        console.log('===========================');
        console.log('--------------------------------------------------');
      }       
}

const postsCount = () => {
    var text = document.getElementById('text');
    var msg = text.value;
    console.log(msg);

    const arr = msg.split("\n")
    console.log(arr);

    

    const posts = document.querySelectorAll('input[name="postsCount"]');
    // console.log(posts);
    for (let p of posts) {
      if (p.checked) {
        console.log(p.value);
      }
    }


  }

  var searchField = document.getElementById('text');
  searchField.addEventListener('keypress', function (e) {
    var key = e.which || e.keyCode;
    if (key === 13) { // код клавиши Enter
        console.log('Press Enter');
    }
});



// tags to <th>Tags</th>
<body>
    <p id="output"></p>
</body>

<script>
window.onload = function(){
    var name = prompt("What's your name?");
    var lengthOfName = name.length

    document.getElementById('output').innerHTML = lengthOfName;
};
</script>
