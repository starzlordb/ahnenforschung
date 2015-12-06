var EventUtils = {
	allowedKeys: [8, 9, 37, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57],
		
	/**
	 * Beim Drücken einer Taste im Datumsfeld
	 */
	keyDown: function(node, event) {
		return this.allowedKeys.indexOf(event.keyCode) != -1;
	},

	/**
	 * Beim Loslassen einer Taste im Datumsfeld
	 */
	keyUp: function(node, event, nextNode) {
		if (event.keyCode == 190) { 
			node.parentNode.getElementsByTagName('input')[nextNode].focus(); 
			node.parentNode.getElementsByTagName('input')[nextNode].select(); 
		}
	},
	
	/**
	 * Family Search Haken
	 */
	familySearch: function(node) {
		var checked = node.getElementsByTagName('input')[0].checked; 
		node.getElementsByTagName('input')[0].checked = !checked; 
		
		if (checked) { 
			node.className = 'familySearch false'; 
		} else { 
			node.className = 'familySearch true'; 
		}
	},
	
	/**
	 * Nachweis
	 */
	nachweis: function(node) {
		var checked = node.getElementsByTagName('input')[0].checked; 
		node.getElementsByTagName('input')[0].checked = !checked; 
		
		if (checked) { 
			node.className = 'nachweis false'; 
		} else { 
			node.className = 'nachweis true'; 
		}
	},
	
	/**
	 * Religionswähler
	 */
	changeReligion: function(node, religion) {
		node.parentNode.parentNode.getElementsByTagName('input')[0].value = religion;
		
		return false;
	},
	
	/**
	 * kopiert Werte von einer Event Row in die andere
	 */
	copyValues: function(sourceRow, destRow) {
		var sourceNode = null;
		
		alert ('TODO');
	},
	
	_decodeHtml: function(html) {
	    var txt = document.createElement("textarea");
	    txt.innerHTML = html;
	    return txt.value;
	},
	
	/**
	 * Suchfunktion ohne Request
	 */
	quickSearch: function(list, search) {
		search = search.toLowerCase().trim().replace(/[^\w\s]/gi, '').split(" ");
		
		try {
			if (document.getElementById('quicksearch').nextSibling.className.search("searchResults") > -1) {
				document.getElementById('quicksearch').nextSibling.style.display = 'none';
			}
		} catch (e) { }
		
		var obj = JSON.parse(list.innerHTML.replace(new RegExp('\'', 'g'), '"'));
		
		var result = '<div class="searchResults" style="z-index: 999;"><table class="searchResultsOuterTable"><tbody>';
		var x = 0;
		
		for (var i = 0; i < obj.length; i++) {
			var exists = [];
			
			for (var j = 0; j < obj[i].length; j++) {
				for (var s = 0; s < search.length; s++) {
					exists[s] |= obj[i][j].toLowerCase().indexOf(search[s]) > -1;
				}
			}
			
			var doExists = true;
			for (var e = 0; e < exists.length; e++) {
				doExists &= exists[e];
			}
			
			if (doExists) {
				result += '<tr class="' + (x++ % 2 == 0 ? 'odd' : 'even') + '"><td><a href="#{request.contextPath}/xhtml/index.xhtml?id=' + obj[i][0] + '">' + obj[i][1] + ", " + obj[i][2] + '</a>' + 
				'<div class="searchResultsBlock"><span class="geburt">' + obj[i][4] + '</span> ' + obj[i][5] + '</div>' + 
				'<div class="searchResultsBlock"><span class="tod">' + obj[i][8] + '</span> ' + obj[i][9] + '</div>' + 
				'</td></tr>';
			}
		}
		
		result += '</tbody></table></div>';
		
		console.log(result);
		
		document.getElementById('quicksearch').innerHTML = result;
	}
}