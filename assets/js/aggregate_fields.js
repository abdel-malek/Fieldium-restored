/* The dragging code for '.draggable' from the demo above
 * applies to this demo as well so it doesn't have to be repeated. */

// enable draggables to be dropped into this
$(document).ready(function () {
    interact('.drag-drop')
            .draggable({
                // enable inertial throwing
                inertia: true,
                // keep the element within the area of it's parent
                restrict: {
                    restriction: "parent",
                    endOnly: true,
                    elementRect: {top: 0, left: 0, bottom: 1, right: 1}
                },
                // enable autoScroll
                autoScroll: true,

                // call this function on every dragmove event
                onmove: dragMoveListener,
                // call this function on every dragend event
                onend: function (event) {
                    var textEl = event.target.querySelector('p');

                    textEl && (textEl.textContent =
                            'moved a distance of '
                            + (Math.sqrt(event.dx * event.dx +
                                    event.dy * event.dy) | 0) + 'px');
                }
            });

    function dragMoveListener(event) {
        var target = event.target,
                // keep the dragged position in the data-x/data-y attributes
                x = (parseFloat(target.getAttribute('data-x')) || 0) + event.dx,
                y = (parseFloat(target.getAttribute('data-y')) || 0) + event.dy;

        // translate the element
        target.style.webkitTransform =
                target.style.transform =
                'translate(' + x + 'px, ' + y + 'px)';

        // update the posiion attributes
        target.setAttribute('data-x', x);
        target.setAttribute('data-y', y);
    }

    // this is used later in the resizing and gesture demos
    window.dragMoveListener = dragMoveListener;


    interact('.dropzone').dropzone({

        accept: '.drag-drop',
        overlap: 0.75,
        ondropactivate: function (event) {
            event.target.classList.add('drop-active');
        },
        ondragenter: function (event) {
            var draggableElement = event.relatedTarget,
                    dropzoneElement = event.target;
            // feedback the possibility of a drop
            dropzoneElement.classList.add('drop-target');
            draggableElement.classList.add('can-drop');
//            draggableElement.textContent = 'Dragged in';
        },
        ondragleave: function (event) {
            // remove the drop feedback style
            event.target.classList.remove('drop-target');
            event.relatedTarget.classList.remove('can-drop');
//            event.relatedTarget.textContent = 'Dragged out';
        },
        ondrop: function (event) {
//            event.relatedTarget.textContent = 'Dropped';
        },
        ondropdeactivate: function (event) {
            // remove active dropzone feedback
            event.target.classList.remove('drop-active');
            event.target.classList.remove('drop-target');
        }
    })
});

function create_aggregate_field() {
    var fields = [];
    $('.can-drop').each(function () {
        fields.push($(this).attr("field_id"));
    });
//    sessionStorage.setItem('fields', JSON.stringify(fields));
    $.ajax({
        url: site_url + '/fields/save_in_session/format/json?fields=' + JSON.stringify(fields),
        type: 'GET',
        async: false,
        success: function (response) {
            if (response.status == true) {
                location.href = site_url + "/fields/fields_management/" + company_id + "/add";
            } else
                show_error("Error in loading bookings");
        }
    });
//    console.log(sessionStorage.getItem('fields'));
//    location.href = site_url + "/fields/fields_management/" + company_id + "/add";
}
console.log(sessionStorage.getItem('fields'));