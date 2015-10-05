window.addEventListener("click",function(event){
            var pagination = $(event.srcElement).closest("ul.pagination").get(0);
            if (pagination){
                for(var i=0,classes,p,array=pagination.children; i<array.length; i++){
                    classes = array[i].getAttribute("class");
                    if(classes){
                        if((p=(classes=classes.split(" ")).indexOf("active"))>=0){
                            classes.splice(p,1);
                            array[i].setAttribute("class",classes.join(" "));
                        }
                    }
                    if(array[i].textContent.trim()==window.scopePage+1)
                        $(array[i]).addClass("active");
                }
            }
        });