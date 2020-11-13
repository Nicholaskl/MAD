function showData(data)
{
    $("#displayarea").text(data);
}

$(window).on("load", function() 
{
    $("#fetchBtn").on("click", function()
    {
    	let url = "data.json";
    	fetch(url, { method: "GET" })
    		.then(function(response) 
    		{
    			if(response.ok) 
    			{
    				return response.json();
    			}
    			else { throw new Error(response.statusText); }
    		})
    		.then(function(data) { showData(formatData(data)); })
    		.catch(err => alert("Oof!"));  
    });
    
    showData("No data retrieved yet");
});
