import { Component, OnInit } from '@angular/core';
import { Topic } from 'src/app/datamodel/topic';
import { TopicService } from 'src/app/services/topic.service';
import { Location } from '@angular/common';
import { AuthenticationService } from 'src/app/services/authentication.service';
import { User } from 'src/app/datamodel/user';

@Component({
  selector: 'app-topic-form',
  templateUrl: './topic-form.component.html',
  styleUrls: ['./topic-form.component.css']
})
export class TopicFormComponent implements OnInit {

  private topicModel = new Topic("");
  private currentUser: User;

  constructor(private authService: AuthenticationService,
    private topicService: TopicService,
    private location: Location) { 
      this.authService.currentUser.subscribe(x => this.currentUser = x);
    }

  ngOnInit() {
  }

  goBack(): void {
    this.location.back();
  }

  createTopic(){
    
    this.topicService.createTopic(this.topicModel).subscribe();
    this.goBack();
  }

}
